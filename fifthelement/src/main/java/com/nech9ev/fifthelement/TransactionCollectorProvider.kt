package com.nech9ev.fifthelement

import android.content.Context
import com.nech9ev.fifthelement.internal.TransactionCollector


object TransactionCollectorProvider {

    @Volatile
    private var collector: TransactionCollector? = null

    @Synchronized
    fun provide(applicationContext: Context, managerConfig: TransactionManagerConfig = TransactionManagerConfig()): TransactionCollector {
        return collector ?: initialize(applicationContext, managerConfig)
    }

    @Synchronized
    private fun initialize(applicationContext: Context, managerConfig: TransactionManagerConfig): TransactionCollector {
        return TransactionCollector(applicationContext, managerConfig).also {
            collector = it
        }
    }
}
