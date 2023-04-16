package com.nech9ev.fifthelement.gateways

import android.content.Context
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.nech9ev.fifthelement.internal.data.NetworkModule
import okhttp3.OkHttpClient
import okhttp3.Request

open class FifthElementWebView(
    applicationContext: Context,
) : WebViewClient() {

    private val okHttpClient: OkHttpClient by lazy {
        NetworkModule.provideOkHttpClient().newBuilder().addNetworkInterceptor(
            FifthElementInterceptor.Builder(applicationContext).build()
        ).build()
    }

    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        view.loadData(okHttpClient.newCall(
            Request.Builder().url(request.url.toString()).build()
        ).execute().body.toString(), "text/html", "UTF-8")
        return true
    }
}