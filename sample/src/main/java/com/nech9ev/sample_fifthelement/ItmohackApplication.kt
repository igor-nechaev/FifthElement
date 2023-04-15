package com.nech9ev.sample_fifthelement

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

class ItmohackApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }
}