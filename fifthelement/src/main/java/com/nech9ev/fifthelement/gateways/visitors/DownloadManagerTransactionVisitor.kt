package com.nech9ev.fifthelement.gateways.visitors

import android.app.DownloadManager
import android.database.Cursor
import android.util.Log
import com.nech9ev.fifthelement.internal.domain.Transaction
import io.ktor.http.*

class DownloadManagerTransactionVisitor {

    fun visitTransaction(cursor: Cursor, downloadId: Long, transaction: Transaction) {
        while (cursor.moveToNext()) {
            val idIndex: Int = cursor.getColumnIndex(DownloadManager.COLUMN_ID)
            val id: Long = cursor.getLong(idIndex)
            if (id != downloadId) {
                if (cursor.isLast) {
                    cursor.close()
                    return
                }
                cursor.move(1)
                continue
            }
            val statusIndex: Int = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
            val status: Int = cursor.getInt(statusIndex)
            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                val sizeIndex: Int = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)
                val uriIndex: Int = cursor.getColumnIndex(DownloadManager.COLUMN_URI)
                val mediaTypeIndex: Int = cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE)
                transaction.responseSize = cursor.getLong(sizeIndex)
                transaction.responseCode = HttpStatusCode.OK.value
                transaction.uri = cursor.getString(uriIndex)
                transaction.requestContentType = cursor.getString(mediaTypeIndex)
                transaction.responseDate = System.currentTimeMillis()
                cursor.close()
                return
            } else {
                val uriIndex: Int = cursor.getColumnIndex(DownloadManager.COLUMN_URI)
                transaction.error = true.toString()
                transaction.uri = cursor.getString(uriIndex)
                transaction.responseDate = System.currentTimeMillis()
                cursor.close()
                return
            }
        }
    }
}
