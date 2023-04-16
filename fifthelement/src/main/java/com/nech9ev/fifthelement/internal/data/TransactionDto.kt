package com.nech9ev.fifthelement.internal.data

import com.nech9ev.fifthelement.internal.domain.NetworkType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class TransactionDto(
    @SerialName("url")
    val url: String? = null,
    @SerialName("uri")
    var uri: String? = null,
    @SerialName("transaction_type")
    var type: NetworkType,
    @SerialName("error")
    var error: String? = null,
    @SerialName("request_date")
    var requestDate: Long? = null,
    @SerialName("response_date")
    var responseDate: Long? = null,
    @SerialName("duration")
    var duration: Long? = null,
    @SerialName("method")
    var requestMethod: String? = null,
    @SerialName("request_content_type")
    var requestContentType: String? = null,
    @SerialName("request_size")
    var requestSize: Long? = null,
    @SerialName("response_code")
    var responseCode: Int? = null,
    @SerialName("response_content_type")
    var responseContentType: String? = null,
    @SerialName("response_size")
    var responseSize: Long? = null,
    @SerialName("threads")
    var threads: String? = null,
)
