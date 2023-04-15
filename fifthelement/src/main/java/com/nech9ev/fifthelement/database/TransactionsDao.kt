package com.nech9ev.fifthelement.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nech9ev.fifthelement.domain.Transaction

@Dao
interface TransactionsDao {

    @Insert
    suspend fun insert(transaction: Transaction): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(transaction: Transaction): Int

    @Query("SELECT * FROM Transactions WHERE id = :id")
    suspend fun getById(id: Long): Transaction?

    @Query("DELETE FROM Transactions WHERE request_date <= :timestamp")
    suspend fun deleteTransactionsCreatedBefore(timestamp: Long): Int

    @Query("SELECT * FROM Transactions WHERE id >= :idFrom AND id <= :idTo")
    suspend fun getTransactionsWithIdBetween(idFrom: Long, idTo: Long): List<Transaction>
}
