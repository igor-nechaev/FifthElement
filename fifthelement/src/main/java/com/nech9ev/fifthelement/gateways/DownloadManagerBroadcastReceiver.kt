package com.nech9ev.fifthelement.gateways

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import com.nech9ev.fifthelement.TransactionCollectorProvider
import com.nech9ev.fifthelement.gateways.visitors.DownloadManagerTransactionVisitor
import com.nech9ev.fifthelement.internal.TransactionCollector
import com.nech9ev.fifthelement.internal.domain.NetworkType
import com.nech9ev.fifthelement.internal.domain.Transaction


class DownloadManagerBroadcastReceiver : BroadcastReceiver() {

    private val dmTransactionVisitor = DownloadManagerTransactionVisitor()
    private lateinit var transactionsCollector: TransactionCollector
    private lateinit var downloadManager: DownloadManager

    override fun onReceive(context: Context, intent: Intent) {
        transactionsCollector = TransactionCollectorProvider.provide(context)
        downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val action = intent.action
        context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        if (action != DownloadManager.ACTION_DOWNLOAD_COMPLETE) return
        val downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)
        if (downloadId == -1L) return

        val query = DownloadManager.Query().apply {
            setFilterById(downloadId)
        }
        val transaction = Transaction(type = NetworkType.DOWNLOAD_MANAGER)
        val cursor: Cursor = downloadManager.query(query)
        dmTransactionVisitor.visitTransaction(cursor, downloadId, transaction)
        transactionsCollector.collectTransaction(transaction)
    }
}
