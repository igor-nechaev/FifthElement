package com.nech9ev.fifthelement

import android.content.Context
import com.nech9ev.fifthelement.internal.TransactionCollector


object TransactionCollectorProvider {

    @Volatile
    private var collector: TransactionCollector? = null

    @Synchronized
    fun provide(applicationContext: Context): TransactionCollector {
        return collector ?: initialize(applicationContext)
    }

    @Synchronized //todo lock on any
    private fun initialize(applicationContext: Context): TransactionCollector {
        return TransactionCollector(applicationContext).also {
            collector = it
        }
    }
}
