package com.nech9ev.fifthelement.gateways.exoplayer

import android.content.Context
import com.google.android.exoplayer2.source.LoadEventInfo
import com.google.android.exoplayer2.source.MediaLoadData
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.MediaSourceEventListener
import com.nech9ev.fifthelement.TransactionCollectorProvider
import com.nech9ev.fifthelement.gateways.visitors.MediaSourceEventErrorTransactionVisitor
import com.nech9ev.fifthelement.gateways.visitors.MediaSourceEventLoadedTransactionVisitor
import com.nech9ev.fifthelement.gateways.visitors.MediaSourceEventStartedTransactionVisitor
import com.nech9ev.fifthelement.internal.TransactionCollector
import com.nech9ev.fifthelement.internal.domain.NetworkType
import com.nech9ev.fifthelement.internal.domain.Transaction
import java.io.IOException

class MediaSourceEventListenerImpl(
    applicationContext: Context,
) : MediaSourceEventListener {

    private val transactionCollector: TransactionCollector by lazy {
        TransactionCollectorProvider.provide(applicationContext)
    }

    private val startedVisitor: MediaSourceEventStartedTransactionVisitor by lazy {
        MediaSourceEventStartedTransactionVisitor()
    }
    private val loadedVisitor: MediaSourceEventLoadedTransactionVisitor by lazy {
        MediaSourceEventLoadedTransactionVisitor()
    }
    private val errorVisitor: MediaSourceEventErrorTransactionVisitor by lazy {
        MediaSourceEventErrorTransactionVisitor()
    }

    @Volatile
    private var transaction: Transaction? = null

    override fun onLoadCompleted(
        windowIndex: Int,
        mediaPeriodId: MediaSource.MediaPeriodId?,
        loadEventInfo: LoadEventInfo,
        mediaLoadData: MediaLoadData,
    ) {
        transaction?.let {
            loadedVisitor.visitTransaction(loadEventInfo.bytesLoaded, mediaLoadData.trackFormat?.label, it)
            transactionCollector.collectResponse(it)
        }
        super.onLoadCompleted(windowIndex, mediaPeriodId, loadEventInfo, mediaLoadData)
    }

    override fun onLoadStarted(
        windowIndex: Int,
        mediaPeriodId: MediaSource.MediaPeriodId?,
        loadEventInfo: LoadEventInfo,
        mediaLoadData: MediaLoadData,
    ) {
        transaction = Transaction(type = NetworkType.EXO_PLAYER)
        transaction?.let {
            startedVisitor.visitTransaction(loadEventInfo.uri, it)
            transactionCollector.collectRequest(it)
        }
        transaction = null
        super.onLoadStarted(windowIndex, mediaPeriodId, loadEventInfo, mediaLoadData)
    }

    override fun onLoadError(
        windowIndex: Int,
        mediaPeriodId: MediaSource.MediaPeriodId?,
        loadEventInfo: LoadEventInfo,
        mediaLoadData: MediaLoadData,
        error: IOException,
        wasCanceled: Boolean,
    ) {
        transaction?.let {
            errorVisitor.visitTransaction(error, it)
            transactionCollector.collectResponse(it)
        }
        transaction = null
        super.onLoadError(windowIndex, mediaPeriodId, loadEventInfo, mediaLoadData, error, wasCanceled)
    }
}