package com.nech9ev.fifthelement.internal.database

import com.nech9ev.fifthelement.internal.database.dao.DeviceConfigDao
import com.nech9ev.fifthelement.internal.database.dao.TransactionsDao
import com.nech9ev.fifthelement.internal.domain.DeviceConfig
import com.nech9ev.fifthelement.internal.domain.Transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


internal class DatabaseRepository(
    private val transactionsDao: TransactionsDao,
    private val deviceConfigDao: DeviceConfigDao,
) {

    init {
        CoroutineScope(Dispatchers.IO).launch {
//            deviceConfigDao.insertDeviceConfig(DeviceConfig(id = DeviceConfigUtils.getDeviceUid(), model = DeviceConfigUtils.getDeviceName()))
        }
    }

    suspend fun insertTransaction(transaction: Transaction): Long {
        return transactionsDao.insert(transaction)
    }

    suspend fun updateTransaction(transaction: Transaction): Long {
        return transactionsDao.update(transaction).toLong()
    }

    suspend fun getDeviceConfig(): DeviceConfig {
        return deviceConfigDao.get()
    }

    suspend fun getTransactionById(id: Long): Transaction? {
        return transactionsDao.getById(id)
    }

    suspend fun getTransactionsWithIdBetween(idFrom: Long, idTo: Long): List<Transaction> {
        return transactionsDao.getTransactionsWithIdBetween(idFrom, idTo)
    }

    suspend fun deleteTransactionsCreatedBefore(timestamp: Long) {
        transactionsDao.deleteTransactionsCreatedBefore(timestamp)
    }

    suspend fun isDeviceConfigNotInitialized(): Boolean {
        return deviceConfigDao.deviceConfigs().isEmpty()
    }

    suspend fun insertDeviceConfig(config: DeviceConfig): Long {
        return deviceConfigDao.insertDeviceConfig(config)
    }
}
