package com.nech9ev.fifthelement.internal

import com.nech9ev.fifthelement.internal.data.NetworkModule
import com.nech9ev.fifthelement.internal.data.NetworkTransactionsRequest
import com.nech9ev.fifthelement.internal.domain.DeviceConfig
import com.nech9ev.fifthelement.internal.domain.Transaction
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class TransactionSender {

    private val client = NetworkModule.provideHttpClient()

    suspend fun sendTransactions(
        transactions: List<Transaction>,
        deviceConfig: DeviceConfig,
        postUrl: String,
    ) = withContext(Dispatchers.IO) {
        try {
            client.post<Any> {
                contentType(ContentType.Application.Json)
                url(postUrl)
                body = NetworkTransactionsRequest(
                    transactions = transactions.map { transaction -> transaction.toDto().also {
                        it.threads = threadNames()
                    } },
                    deviceConfig = deviceConfig,
                )
            }
            true
        } catch (e: Throwable) {
            false
        }
    }

    private fun threadNames(): String {
        val threads = arrayOfNulls<Thread>(Thread.activeCount())
        val numThreads = Thread.enumerate(threads)
        val threadNames = StringBuilder()
        for (i in 0 until numThreads) {
            threadNames.append("${threads[i]?.name ?: "unknown"} ;")
        }
        return threadNames.toString()
    }
}
