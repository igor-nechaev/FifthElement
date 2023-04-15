package com.nech9ev.fifthelement.visitors

import com.nech9ev.fifthelement.domain.Transaction
import okhttp3.Response

class HttpResponseTransactionVisitor {

    fun visitTransaction(response: Response, transaction: Transaction) {
        transaction.responseDate = response.receivedResponseAtMillis
        transaction.responseCode = response.code
        transaction.responseSize = response.headers.byteCount() + (response.body?.contentLength() ?: 0)
        transaction.responseContentType = response.header("Content-Type")
        transaction.duration = (response.receivedResponseAtMillis - response.sentRequestAtMillis)
    }
}
