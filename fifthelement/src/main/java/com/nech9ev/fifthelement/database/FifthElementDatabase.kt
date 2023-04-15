package com.nech9ev.fifthelement.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nech9ev.fifthelement.domain.DeviceConfig
import com.nech9ev.fifthelement.domain.Transaction


@Database(entities = [DeviceConfig::class, Transaction::class], version = 1, exportSchema = false)
abstract class FifthElementDatabase : RoomDatabase() {

    abstract fun transactionsDao(): TransactionsDao

    abstract fun deviceDao(): DeviceConfigDao

    companion object {

        fun createDatabase(applicationContext: Context): FifthElementDatabase {
            return Room.databaseBuilder(applicationContext, FifthElementDatabase::class.java, "fifth_element.db")
                .fallbackToDestructiveMigration() //todo remove
                .build()
        }
    }
}
