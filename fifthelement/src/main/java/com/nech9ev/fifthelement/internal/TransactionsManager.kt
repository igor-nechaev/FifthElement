package com.nech9ev.fifthelement.internal

import android.content.Context
import com.nech9ev.fifthelement.TransactionManagerConfig
import com.nech9ev.fifthelement.internal.database.DatabaseRepository
import com.nech9ev.fifthelement.internal.database.DatabaseRepositoryProvider
import com.nech9ev.fifthelement.internal.domain.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicLong

class TransactionsManager(
    applicationContext: Context,
    private val managerConfig: TransactionManagerConfig,
) {

    private val transactionsUploadSize: AtomicLong = AtomicLong().apply {
        set(managerConfig.transactionsUploadSize)
    }

    private val lastNotSendId: AtomicLong = AtomicLong().apply {
        set(initialNotSendTransactionId)
    }

    private val repository: DatabaseRepository =
        DatabaseRepositoryProvider.provide(applicationContext)

    private val transactionsSender = TransactionSender()

    suspend fun onNewTransaction(transaction: Transaction) = withContext(Dispatchers.IO) {

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
            deviceConfig = repository.getDeviceConfig(),
            postUrl = managerConfig.postUrl,
        )

        handleSendResult(isSuccess, transaction)
    }

    private fun shouldSendRequests(transaction: Transaction): Boolean {
        return ((transaction.id - lastNotSendId.get()) % transactionsUploadSize.get()) == 0L
                && transaction.error == null
    }

    private suspend fun handleSendResult(isSuccess: Boolean, lastTransaction: Transaction) {
        if (isSuccess) {
            handleSuccessSend(lastTransaction)
            lastNotSendId.set(initialNotSendTransactionId)
        }
    }

    private suspend fun handleSuccessSend(lastTransaction: Transaction) {
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
