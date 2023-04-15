package com.nech9ev.fifthelement.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import okhttp3.Response


@Serializable
data class NetworkResponse(
    @SerialName("timestamp")
    private val timestamp: Long,
    @SerialName("bytes")
    private val bytes: Long,
    @SerialName("content_type")
    private val contentType: String? = null,
) {

    companion object {

        fun createFromHttpResponse(response: Response) =
            NetworkResponse(
                timestamp = timestamp(),
                bytes = response.body?.contentLength() ?: -1,
                contentType = response.request.header("Content-Type") ?: "Unknown",
            )

        private fun timestamp() = System.currentTimeMillis()
    }
}