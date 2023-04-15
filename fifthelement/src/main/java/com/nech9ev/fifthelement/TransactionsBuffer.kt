package com.nech9ev.fifthelement

import android.util.Log
import androidx.collection.CircularArray
import com.nech9ev.fifthelement.domain.NetworkTransaction

class TransactionsBuffer(
    bufferCapacity: Int,
    private val requestsSizeForUpload: Int,
    private val trySendRequests: (List<NetworkTransaction>) -> Boolean,
) {

    private var circularBuffer: CircularArray<NetworkTransaction> = CircularArray<NetworkTransaction>(bufferCapacity)
    private val preparedRequests: MutableList<NetworkTransaction> = ArrayList(requestsSizeForUpload)

    fun addRequest(request: NetworkTransaction) {
        Log.e("TransactionsBuffer", "tryAdd")
        if (tryAdd(request)) {
            while (tryUpload()) {
                Log.e("TransactionsBuffer", "fillListFromBuffer")
                fillListFromBuffer()
            }
        }
        Log.e("TransactionsBuffer", "tryUpload")
        while (tryUpload()) {
            Log.e("TransactionsBuffer", "fillListFromBuffer")
            fillListFromBuffer()
        }
        Log.e("TransactionsBuffer", "circularBuffer.addLast(")
        circularBuffer.addLast(request)
    }

    private fun tryAdd(request: NetworkTransaction): Boolean {
        if (preparedRequests.size < requestsSizeForUpload) {
            preparedRequests.add(request)
            return true
        }
        return false
    }

    private fun tryUpload(): Boolean {
        if (preparedRequests.size == requestsSizeForUpload && trySendRequests(preparedRequests.toMutableList())) {
            preparedRequests.clear()
            return true
        }
        return false
    }

    private fun fillListFromBuffer() {
        repeat(circularBuffer.size().coerceAtMost(requestsSizeForUpload)) {
            preparedRequests.add(circularBuffer.popFirst())
        }
    }
}
