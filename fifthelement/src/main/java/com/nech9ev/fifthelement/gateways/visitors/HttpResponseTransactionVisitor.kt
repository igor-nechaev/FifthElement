package com.nech9ev.fifthelement.gateways.visitors

import com.nech9ev.fifthelement.internal.domain.Transaction
import okhttp3.Response

class HttpResponseTransactionVisitor {

    fun visitTransaction(response: Response, transaction: Transaction) {
        transaction.responseDate = response.receivedResponseAtMillis
        transaction.responseCode = response.code
        transaction.responseSize = response.headers.byteCount() + (response.body?.contentLength() ?: 0)
        transaction.responseContentType = response.header("Content-Type")
        transaction.duration = (response.receivedResponseAtMillis - response.sentRequestAtMillis)
    }

    fun visitFailedTransaction(transaction: Transaction, throwable: Throwable) {
        transaction.error = throwable.message
        transaction.responseDate = System.currentTimeMillis()
        transaction.requestDate?.let { requestDate ->
            transaction.duration = transaction.responseDate!! - requestDate
        } ?: {
            transaction.duration = 0L
        }
    }
}
