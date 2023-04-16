package com.nech9ev.fifthelement.internal.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nech9ev.fifthelement.internal.data.TransactionDto


@Entity(tableName = "Transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @kotlinx.serialization.Transient
    var id: Long = 0,
    @ColumnInfo(name = "transaction_type")
    var type: NetworkType,
    @ColumnInfo(name = "url")
    var url: String? = null,
    @ColumnInfo(name = "uri")
    var uri: String? = null,
    @ColumnInfo(name = "error")
    var error: String? = null,
    @ColumnInfo(name = "request_date")
    var requestDate: Long? = null,
    @ColumnInfo(name = "response_date")
    var responseDate: Long? = null,
    @ColumnInfo(name = "duration")
    var duration: Long? = null,
    @ColumnInfo(name = "method")
    var requestMethod: String? = null,
    @ColumnInfo(name = "request_content_type")
    var requestContentType: String? = null,
    @ColumnInfo(name = "request_size")
    var requestSize: Long? = null,
    @ColumnInfo(name = "response_code")
    var responseCode: Int? = null,
    @ColumnInfo(name = "response_content_type")
    var responseContentType: String? = null,
    @ColumnInfo(name = "response_size")
    var responseSize: Long? = null,
) {
    internal fun toDto() = TransactionDto(
        url, uri, type, error, requestDate, responseDate, duration, requestMethod, requestContentType, requestSize, responseCode, responseContentType, responseSize
    )
}
