package com.nech9ev.fifthelement.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class NetworkType {
    @SerialName("http_client")
    HTTP_CLIENT,
    @SerialName("download_manager")
    DOWNLOAD_MANAGER,
    @SerialName("exo_player")
    EXO_PLAYER,
}