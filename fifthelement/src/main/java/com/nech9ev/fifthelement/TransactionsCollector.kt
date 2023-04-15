package com.nech9ev.fifthelement

import android.util.Log
import com.nech9ev.fifthelement.domain.NetworkTransaction
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch


class TransactionsCollector(
    bufferCapacity: Int,
    requestsSizeForUpload: Int,
) {
    private val bufferDecoration = TransactionsBuffer(bufferCapacity, requestsSizeForUpload, ::trySendRequests)

    private val transactionsChannel: Channel<List<NetworkTransaction>> = Channel(
        capacity = requestsSizeForUpload,
        onBufferOverflow = BufferOverflow.SUSPEND,
    )

    private val transactionsFlow = transactionsChannel.receiveAsFlow()

    private val scope = CoroutineScope(Job())
    private val manager = TransactionsManager()

    init {
        scope.launch(Dispatchers.IO + CoroutineName("handleTransactions")) {
            while (isActive) {
                transactionsFlow.collect { transactions ->
                    transactions.takeIf { transactions.isNotEmpty() }
                        .let {
                            Log.e("TransactionsCollector", "receive ${transactions.size}")
                            Log.e("TransactionsCollector", "addTransactions ${transactions.forEach(NetworkTransaction::toString)}")
                            manager.addTransactions(transactions)
                        }
                }
            }
        }
    }

    fun addTransaction(transaction: NetworkTransaction) {
        bufferDecoration.addRequest(transaction)
    }

    private fun trySendRequests(transactions: List<NetworkTransaction>): Boolean {
        Log.e("TransactionsCollector", "trySendTransactions")
        return transactionsChannel.trySend(transactions).isSuccess
    }
}
