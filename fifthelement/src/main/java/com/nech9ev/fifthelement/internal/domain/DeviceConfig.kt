package com.nech9ev.fifthelement.internal.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "DeviceConfig")
@Serializable
internal data class DeviceConfig(
    @SerialName("id")
    @ColumnInfo(name = "device_id")
    var id: String,
    @SerialName("model")
    @ColumnInfo(name = "model")
    @PrimaryKey
    var model: String,
)
