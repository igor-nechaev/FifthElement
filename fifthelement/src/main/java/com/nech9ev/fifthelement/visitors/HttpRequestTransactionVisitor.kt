package com.nech9ev.fifthelement.visitors

import com.nech9ev.fifthelement.domain.Transaction
import okhttp3.Request

class HttpRequestTransactionVisitor {

    fun visitTransaction(request: Request, transaction: Transaction) {
        transaction.url = request.url.toString()
        transaction.requestSize = request.headers.byteCount() + (request.body?.contentLength() ?: 0)
        transaction.requestDate = System.currentTimeMillis()
        transaction.requestContentType = request.body?.contentType()?.toString()
        transaction.requestMethod = request.method
    }
}
