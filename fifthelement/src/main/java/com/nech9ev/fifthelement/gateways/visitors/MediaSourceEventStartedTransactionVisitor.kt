package com.nech9ev.fifthelement.gateways.visitors

import android.net.Uri
import com.nech9ev.fifthelement.internal.domain.Transaction
import okhttp3.Response

class MediaSourceEventStartedTransactionVisitor {

    fun visitTransaction(uri: Uri, transaction: Transaction) {
        transaction.requestDate = System.currentTimeMillis()
        transaction.uri = uri.toString()
    }
}
