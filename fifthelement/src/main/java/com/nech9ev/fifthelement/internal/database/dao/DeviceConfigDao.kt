package com.nech9ev.fifthelement.internal.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nech9ev.fifthelement.internal.domain.DeviceConfig

@Dao
internal interface DeviceConfigDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertDeviceConfig(deviceConfig: DeviceConfig): Long

    @Query("SELECT * FROM DeviceConfig")
    suspend fun deviceConfigs(): List<DeviceConfig>

    @Query("SELECT * FROM DeviceConfig")
    suspend fun get(): DeviceConfig
}
