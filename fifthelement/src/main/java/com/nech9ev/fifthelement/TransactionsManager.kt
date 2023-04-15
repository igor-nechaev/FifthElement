package com.nech9ev.fifthelement

import android.util.Log
import com.nech9ev.fifthelement.data.DeviceConfigUtils
import com.nech9ev.fifthelement.database.DatabaseRepositoryProvider
import com.nech9ev.fifthelement.domain.DeviceConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicLong

class TransactionsManager(
    transactionsUploadSize: Long = 2L,
) {

    private val transactionsSender = TransactionSender()

    private val transactionsUploadSize: AtomicLong = AtomicLong().apply {
        set(transactionsUploadSize)
    }

    private val transactionsIdLastNotSend: AtomicLong = AtomicLong().apply {
        set(-1L)
    }

    private lateinit var deviceConfig: DeviceConfig

    fun setDeviceConfig(config: DeviceConfig) {
        deviceConfig = config
    }

    suspend fun onNewTransaction(transactionId: Long) = withContext(Dispatchers.IO) {
        Log.e("TransactionsManager", "transactionId: $transactionId")
        Log.e("TransactionsManager", "transactionsIdLastNotSend: ${transactionsIdLastNotSend.get()}")
        if ((transactionId % transactionsUploadSize.get()) != 0L) {
            if (transactionsIdLastNotSend.get() == -1L) {
                transactionsIdLastNotSend.set(transactionId)
            }
        }
        val repository = DatabaseRepositoryProvider.provide()
        val isSuccess = transactionsSender.sendTransactions(
            transactions = repository
                .getTransactionsWithIdBetween(
                    idFrom = transactionsIdLastNotSend.get(),
                    idTo = transactionId,
                ),
            deviceConfig = DeviceConfig(id = DeviceConfigUtils.getDeviceUid(), model = DeviceConfigUtils.getDeviceName()),
        )
        Log.e("TransactionsManager", "sendTransactions isSucces: $isSuccess")
    }
}
