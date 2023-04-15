package com.nech9ev.fifthelement

import android.os.Build
import android.util.Log
import com.nech9ev.fifthelement.data.NetworkModule
import com.nech9ev.fifthelement.data.NetworkTransactionsRequest
import com.nech9ev.fifthelement.domain.NetworkTransaction
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class TransactionsManager {

    private val client = NetworkModule.provideHttpClient()

    suspend fun addTransactions(transactions: List<NetworkTransaction>) = withContext(Dispatchers.IO) {
        Log.e("TransactionsManager", "NetworkTransaction start")
        transactions.forEach {
            it.deviceModel = getDeviceName()
            Log.e("TransactionsManager", it.toString())
        }
        try {
            client.post {
                contentType(ContentType.Application.Json)
                url("http://172.20.10.5:8080/api/write/networkTraffic")
                body = NetworkTransactionsRequest(transactions)
            }
        } catch (e: Throwable) {
            Log.e("TransactionsManager", "NetworkTransaction exception ${e.message}")
        }

        Log.e("TransactionsManager", "NetworkTransaction end")
    }


    //todo create utils + device id from room
    private suspend fun getDeviceName(): String {
        val manufacturer: String = Build.MANUFACTURER
        val model: String = Build.MODEL
        return if (model.lowercase(Locale.getDefault()).startsWith(manufacturer.lowercase(Locale.getDefault()))) {
            capitalize(model)
        } else {
            capitalize(manufacturer) + " " + model
        }
    }

    private fun capitalize(s: String?): String {
        if (s == null || s.isEmpty()) {
            return ""
        }
        val first = s[0]
        return if (Character.isUpperCase(first)) {
            s
        } else {
            first.uppercaseChar().toString() + s.substring(1)
        }
    }
}
