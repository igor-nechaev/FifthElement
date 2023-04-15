package com.nech9ev.fifthelement

import android.util.Log
import com.nech9ev.fifthelement.data.NetworkModule
import com.nech9ev.fifthelement.data.NetworkTransactionsRequest
import com.nech9ev.fifthelement.domain.DeviceConfig
import com.nech9ev.fifthelement.domain.Transaction
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TransactionSender {

    private val client = NetworkModule.provideHttpClient()

    suspend fun sendTransactions(
        transactions: List<Transaction>,
        deviceConfig: DeviceConfig,
    ) = withContext(Dispatchers.IO) {
        transactions.forEach {
            Log.e("TransactionSender", "sendTransaction: $it" )
        }
        try {
            client.post<Any> {
                contentType(ContentType.Application.Json)
                url("http://172.20.10.5:8080/api/write/networkTraffic")
                body = NetworkTransactionsRequest(
                    transactions = transactions.map { transaction -> transaction.toDto() },
                    deviceConfig = deviceConfig,
                )
            }
            true
        } catch (e: Throwable) {
            false
        }
    }
}
