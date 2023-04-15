package com.nech9ev.fifthelement.data

import com.nech9ev.fifthelement.domain.DeviceConfig
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkTransactionsRequest(
    @SerialName("device_config")
    val deviceConfig: DeviceConfig,
    @SerialName("transactions")
    val transactions: List<TransactionDto>,
)