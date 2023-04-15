package com.nech9ev.fifthelement.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nech9ev.fifthelement.domain.DeviceConfig

@Dao
interface DeviceConfigDao {

//    @Query("INSERT INTO DeviceConfig VALUES(:id,:model) WHERE NOT EXISTS (SELECT * FROM DeviceConfig)")
    @Insert
    suspend fun insertDeviceConfig(deviceConfig: DeviceConfig): Long?

    @Query("SELECT * FROM DeviceConfig")
    suspend fun get(): DeviceConfig
}
