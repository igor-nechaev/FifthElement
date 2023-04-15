package com.nech9ev.fifthelement

import android.content.Context
import android.util.Log
import com.nech9ev.fifthelement.database.DatabaseRepository
import com.nech9ev.fifthelement.database.DatabaseRepositoryProvider
import com.nech9ev.fifthelement.domain.Transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionCollector(
    context: Context,
) {
    private val transactionsManager = TransactionsManager()
    private val scope = CoroutineScope(Job())

    init {
        DatabaseRepositoryProvider.initialize(context)

        scope.launch(Dispatchers.IO) {
            transactionsManager.setDeviceConfig(
                DatabaseRepositoryProvider.provide()
                    .getDeviceConfig()
            )
        }
    }

    fun collectRequest(transaction: Transaction) {
        Log.e("TransactionCollector", "collectRequest: $transaction")
        scope.launch {
            val id: Long = withContext(Dispatchers.IO) {
                DatabaseRepositoryProvider.provide()
                    .insertTransaction(transaction)
            }
            transaction.id = id
        }
    }

    fun collectResponse(transaction: Transaction) {
        Log.e("TransactionCollector", "collectResponse: $transaction")
        scope.launch {
            withContext(Dispatchers.IO) {
                DatabaseRepositoryProvider.provide().updateTransaction(transaction)
            }
            transactionsManager.onNewTransaction(transaction.id)
        }
    }
}
