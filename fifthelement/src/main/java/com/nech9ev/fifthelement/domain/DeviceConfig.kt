package com.nech9ev.fifthelement.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "DeviceConfig")
@Serializable
data class DeviceConfig(
    @SerialName("device_id")
    @ColumnInfo(name = "device_id")
    var id: String,
    @SerialName("model")
    @ColumnInfo(name = "model")
    @PrimaryKey
    var model: String,
)
