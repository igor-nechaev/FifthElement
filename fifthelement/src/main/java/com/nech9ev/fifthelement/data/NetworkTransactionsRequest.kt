package com.nech9ev.fifthelement.data

import com.nech9ev.fifthelement.domain.NetworkTransaction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkTransactionsRequest(
    @SerialName("transactions")
    val transactions: List<NetworkTransaction>,
)