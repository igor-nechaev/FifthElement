package com.nech9ev.fifthelement.database

import com.nech9ev.fifthelement.data.DeviceConfigUtils
import com.nech9ev.fifthelement.domain.DeviceConfig
import com.nech9ev.fifthelement.domain.Transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DatabaseRepository(
    private val transactionsDao: TransactionsDao,
    private val deviceConfigDao: DeviceConfigDao,
) {

    init {
//        CoroutineScope(Dispatchers.IO).launch {
//            deviceConfigDao.insertDeviceConfig(DeviceConfig(id = DeviceConfigUtils.getDeviceUid(), model = DeviceConfigUtils.getDeviceName()))
//        }
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
}
