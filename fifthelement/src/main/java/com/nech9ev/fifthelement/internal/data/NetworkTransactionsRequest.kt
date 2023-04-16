package com.nech9ev.fifthelement.internal.data

import com.nech9ev.fifthelement.internal.domain.DeviceConfig
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class NetworkTransactionsRequest(
    @SerialName("device_info")
    val deviceConfig: DeviceConfig,
    @SerialName("transactions")
    val transactions: List<TransactionDto>,
)