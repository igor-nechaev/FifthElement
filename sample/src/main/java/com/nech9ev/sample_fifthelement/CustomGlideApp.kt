package com.nech9ev.sample_fifthelement


import android.content.Context
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.Excludes
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.nech9ev.sample_fifthelement.retrofit.RetrofitProvider
import java.io.InputStream


@GlideModule
@Excludes(OkHttpLibraryGlideModule::class)
class CustomGlideApp : AppGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        Log.e("Glide", "registerComponents")
        val client = RetrofitProvider.provideOkhttpClient(context)
        val factory: OkHttpUrlLoader.Factory = OkHttpUrlLoader.Factory(client)
        glide.registry.replace(
            GlideUrl::class.java,
            InputStream::class.java,
            factory,
        )
    }
}
