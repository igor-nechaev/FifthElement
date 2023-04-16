package com.nech9ev.sample_fifthelement

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.jakewharton.picasso.OkHttp3Downloader
import com.nech9ev.sample_fifthelement.retrofit.RetrofitProvider
import com.squareup.picasso.Picasso

class ItmohackApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeFresco()
        initializePicasso()
    }

    private fun initializeFresco() {
        val config: ImagePipelineConfig = OkHttpImagePipelineConfigFactory
                .newBuilder(applicationContext, RetrofitProvider.provideOkhttpClient(applicationContext))
                .build()
        Fresco.initialize(applicationContext, config);
    }

    private fun initializePicasso() {
        val picasso: Picasso = Picasso.Builder(applicationContext)
            .downloader(OkHttp3Downloader(RetrofitProvider.provideOkhttpClient(applicationContext)))
            .build()
        Picasso.setSingletonInstance(picasso)
    }
}