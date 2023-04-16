package com.nech9ev.fifthelement.gateways.visitors

import com.nech9ev.fifthelement.internal.domain.Transaction
import io.ktor.http.*

class MediaSourceEventLoadedTransactionVisitor {

    fun visitTransaction(responseSize: Long, responseContentType: String?, transaction: Transaction) {
        transaction.responseDate = System.currentTimeMillis()
        transaction.responseCode = HttpStatusCode.OK.value
        transaction.responseSize = responseSize
        transaction.responseContentType = responseContentType
        transaction.duration = transaction.responseDate!! - transaction.requestDate!!
    }
}