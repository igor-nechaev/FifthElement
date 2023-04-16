package com.nech9ev.fifthelement.gateways.visitors

import com.nech9ev.fifthelement.internal.domain.Transaction
import java.io.IOException

class MediaSourceEventErrorTransactionVisitor {

    fun visitTransaction(exception: IOException, transaction: Transaction) {
        transaction.error = exception.message
    }
}