package com.nech9ev.fifthelement

import android.content.Context
import com.nech9ev.fifthelement.domain.NetworkType
import com.nech9ev.fifthelement.domain.Transaction
import com.nech9ev.fifthelement.visitors.HttpRequestTransactionVisitor
import com.nech9ev.fifthelement.visitors.HttpResponseTransactionVisitor
import okhttp3.Interceptor
import okhttp3.Response


class FifthElementInterceptor(
    builder: Builder,
) : Interceptor {

    private val transactionsCollector = TransactionCollector(
        builder.applicationContext,
    )

    private val requestVisitor = HttpRequestTransactionVisitor()
    private val responseVisitor = HttpResponseTransactionVisitor()

    override fun intercept(chain: Interceptor.Chain): Response {
        val transaction = Transaction(type = NetworkType.HTTP_CLIENT)
        val request = chain.request()

        requestVisitor.visitTransaction(request, transaction)
        transactionsCollector.collectRequest(transaction)

        val response = try {
            chain.proceed(request)
        } catch (exception: Throwable) {
            transaction.error = exception.toString()
            transactionsCollector.collectResponse(transaction)
            throw exception
        }

        responseVisitor.visitTransaction(response, transaction)
        transactionsCollector.collectResponse(transaction)

        return response
    }

    class Builder(
        val applicationContext: Context,
    ) {

        internal var bufferCapacity: Int = 20
        internal var requestsSizeForUpload: Int = 5

        fun bufferCapacity(capacity: Int): Builder = apply {
            bufferCapacity = capacity
        }

        fun requestsSizeForUpload(size: Int): Builder = apply {
            requestsSizeForUpload = size
        }

        fun build(): FifthElementInterceptor = FifthElementInterceptor(this)
    }
}
