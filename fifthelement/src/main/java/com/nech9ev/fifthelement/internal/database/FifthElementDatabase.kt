package com.nech9ev.fifthelement.internal.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nech9ev.fifthelement.internal.database.dao.DeviceConfigDao
import com.nech9ev.fifthelement.internal.database.dao.TransactionsDao
import com.nech9ev.fifthelement.internal.domain.DeviceConfig
import com.nech9ev.fifthelement.internal.domain.Transaction


@Database(entities = [DeviceConfig::class, Transaction::class], version = 1, exportSchema = false)
internal abstract class FifthElementDatabase : RoomDatabase() {

    internal abstract fun transactionsDao(): TransactionsDao

    internal abstract fun deviceDao(): DeviceConfigDao

    companion object {

        internal fun createDatabase(applicationContext: Context): FifthElementDatabase {
            return Room.databaseBuilder(applicationContext, FifthElementDatabase::class.java, "fifth_element.db")
                .build()
        }
    }
}
