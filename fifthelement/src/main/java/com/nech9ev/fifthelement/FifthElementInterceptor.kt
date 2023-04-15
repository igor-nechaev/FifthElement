package com.nech9ev.fifthelement

import com.nech9ev.fifthelement.domain.NetworkRequest
import com.nech9ev.fifthelement.domain.NetworkResponse
import com.nech9ev.fifthelement.domain.NetworkTransaction
import com.nech9ev.fifthelement.domain.NetworkType
import okhttp3.Interceptor
import okhttp3.Response

class FifthElementInterceptor(
    builder: Builder,
) : Interceptor {

    private val transactionsCollector = TransactionsCollector(
        builder.bufferCapacity,
        builder.requestsSizeForUpload,
    )

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        transactionsCollector.addTransaction(
            NetworkTransaction(
                type = NetworkType.HTTP_CLIENT,
                request = NetworkRequest.createFromHttpRequest(request),
                response = NetworkResponse.createFromHttpResponse(response)
            )
        )
        return response
    }

    class Builder {

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
