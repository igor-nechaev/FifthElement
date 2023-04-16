package com.nech9ev.fifthelement.internal

import android.content.Context
import android.util.Log
import com.nech9ev.fifthelement.internal.database.DatabaseRepository
import com.nech9ev.fifthelement.internal.database.DatabaseRepositoryProvider
import com.nech9ev.fifthelement.internal.domain.DeviceConfig
import com.nech9ev.fifthelement.internal.domain.Transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicLong

class TransactionsManager(
    applicationContext: Context,
    transactionsUploadSize: Long = 2L,
) {

    private val transactionsUploadSize: AtomicLong = AtomicLong().apply {
        set(transactionsUploadSize)
    }

    private val lastNotSendId: AtomicLong = AtomicLong().apply {
        set(initialNotSendTransactionId)
    }

    private val repository: DatabaseRepository =
        DatabaseRepositoryProvider.provide(applicationContext)

    private val transactionsSender = TransactionSender()

    private lateinit var deviceConfig: DeviceConfig

    init {
        CoroutineScope(Dispatchers.IO).launch {
            deviceConfig = repository.getDeviceConfig()
        }
    }

    suspend fun onNewTransaction(transaction: Transaction) = withContext(Dispatchers.IO) {
        Log.e("TransactionsManager", "onNewTransaction: $transaction")
        Log.e("TransactionsManager", "IdLastNotSend: ${lastNotSendId.get()}")

        if (isFirstTransactionInBatch()) {
            lastNotSendId.set(transaction.id)
            return@withContext
        }

        if (!shouldSendRequests(transaction)) {
            return@withContext
        }
        val isSuccess = transactionsSender.sendTransactions(
            transactions = repository
                .getTransactionsWithIdBetween(
                    idFrom = lastNotSendId.get(),
                    idTo = transaction.id,
                ),
            deviceConfig = deviceConfig,
        )
        Log.e("TransactionsManager", "sendTransactions isSuccess: $isSuccess")

        handleSendResult(isSuccess, transaction)
    }

    private fun shouldSendRequests(transaction: Transaction): Boolean {
        return ((transaction.id - lastNotSendId.get()) % transactionsUploadSize.get()) == 0L
                    && transaction.error == null
    }

    private suspend fun handleSendResult(isSuccess : Boolean, lastTransaction: Transaction) {
        if (isSuccess) {
            Log.e("TransactionsManager", "SUCCESS SEND")
            handleSuccessSend(lastTransaction)
            lastNotSendId.set(initialNotSendTransactionId)
        }
    }

    private suspend fun handleSuccessSend(lastTransaction: Transaction) {
        Log.e("TransactionsManager", lastTransaction.toString())
        lastTransaction.requestDate?.let {
            repository.deleteTransactionsCreatedBefore(it)
            return
        }
        lastTransaction.responseDate?.let {
            repository.deleteTransactionsCreatedBefore(it)
        }
    }

    private fun isFirstTransactionInBatch() = lastNotSendId.get() == initialNotSendTransactionId

    companion object {

        const val initialNotSendTransactionId = -1L
    }
}
