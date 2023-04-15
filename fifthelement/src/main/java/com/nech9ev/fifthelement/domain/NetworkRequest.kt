package com.nech9ev.fifthelement.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import okhttp3.Request


@Serializable
data class NetworkRequest(
    @SerialName("timestamp")
    private val timestamp: Long,
    @SerialName("bytes")
    private val bytes: Long,
    @SerialName("url")
    private val url: String ?= null,
    @SerialName("uri")
    private val uri: String ?= null,
    @SerialName("method")
    private val method: String ?= null,
    @SerialName("content_type")
    private val contentType: String? = null,
) {

    companion object {

        fun createFromHttpRequest(request: Request) =
            NetworkRequest(
                url = request.url.toString(),
                method = request.method,
                timestamp = timestamp(),
                contentType = request.body?.contentType()?.toString(),
                bytes = request.headers.byteCount() + (request.body?.contentLength() ?: 0),
            )

        private fun timestamp() = System.currentTimeMillis()
    }
}
