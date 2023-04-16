package com.nech9ev.sample_fifthelement.retrofit

import android.content.Context
import com.google.gson.GsonBuilder
import com.nech9ev.fifthelement.gateways.FifthElementInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitProvider {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Volatile
    private var okHttpClient: OkHttpClient? = null

    @Volatile
    private var fifthElementInterceptor: FifthElementInterceptor? = null

    @Synchronized
    fun provideRetrofit(applicationContext: Context): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = provideOkhttpClient(applicationContext)
        val gson = GsonBuilder()
            .setPrettyPrinting()
            .create()
        val gsonConverterFactory = GsonConverterFactory.create(gson)
        val rxJavaCallAdapterFactory = RxJava3CallAdapterFactory.create()

        return Retrofit.Builder()
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxJavaCallAdapterFactory)
            .baseUrl("https://catfact.ninja/")
            .client(okHttpClient)
            .build()
    }

    @Synchronized
    fun provideOkhttpClient(applicationContext: Context) = okHttpClient ?: OkHttpClient.Builder()
        .addInterceptor(provideLoggingInterceptor())
        .addNetworkInterceptor(
            provideFifthElementInterceptor(applicationContext)
        )
        .connectTimeout(5, TimeUnit.SECONDS)
        .build().also {
            okHttpClient = it
        }

    @Synchronized
    fun provideLoggingInterceptor() = loggingInterceptor

    @Synchronized
    fun provideFifthElementInterceptor(applicationContext: Context) =
        fifthElementInterceptor ?: FifthElementInterceptor.Builder(applicationContext)
            .bufferCapacity(3)
            .requestsSizeForUpload(1)
            .build().also {
                fifthElementInterceptor = it
            }
}
