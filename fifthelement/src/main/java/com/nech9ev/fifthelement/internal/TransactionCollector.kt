package com.nech9ev.fifthelement.internal

import android.content.Context
import com.nech9ev.fifthelement.internal.data.DeviceConfigUtils
import com.nech9ev.fifthelement.internal.database.DatabaseRepositoryProvider
import com.nech9ev.fifthelement.internal.domain.Transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class TransactionCollector(
    applicationContext: Context,
) {
    private val transactionsManager = TransactionsManager(applicationContext)
    private val scope = CoroutineScope(Job())

    private val databaseRepository = DatabaseRepositoryProvider.provide(applicationContext)

    init {
        scope.launch(Dispatchers.IO) {
            if (databaseRepository.isDeviceConfigNotInitialized()) {
                databaseRepository.insertDeviceConfig(
                    DeviceConfigUtils.getDeviceConfig()
                )
            }
        }
    }

    @Synchronized //todo lock on object (not class)
    fun collectRequest(transaction: Transaction) {
        scope.launch {
            val id: Long = withContext(Dispatchers.IO) {
                databaseRepository.insertTransaction(transaction)
            }
            transaction.id = id
        }
    }

    @Synchronized
    fun collectResponse(transaction: Transaction) {
        scope.launch {
            withContext(Dispatchers.IO) {
                databaseRepository.updateTransaction(transaction)
            }
            transactionsManager.onNewTransaction(transaction)
        }
    }

    @Synchronized
    fun collectTransaction(transaction: Transaction) {
        scope.launch {
            val id: Long = withContext(Dispatchers.IO) {
                databaseRepository.insertTransaction(transaction)
            }
            transaction.id = id
            transactionsManager.onNewTransaction(transaction)
        }
    }
}
