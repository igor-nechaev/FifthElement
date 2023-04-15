package com.nech9ev.fifthelement.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class NetworkTransaction(
    @SerialName("transactionType")
    val type: NetworkType,
    @SerialName("deviceModel")
    var deviceModel: String ?= null,
    @SerialName("request")
    val request: NetworkRequest? = null,
    @SerialName("response")
    val response: NetworkResponse? = null,
) {

    override fun toString(): String {
        return "${type.name}\n$request\n$response"
    }
}