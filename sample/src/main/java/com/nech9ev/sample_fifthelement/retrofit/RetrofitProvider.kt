package com.nech9ev.sample_fifthelement.retrofit

import android.content.Context
import com.google.gson.GsonBuilder
import com.nech9ev.fifthelement.FifthElementInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitProvider {

    fun provideRetrofit(applicationContext: Context): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addNetworkInterceptor(
                FifthElementInterceptor.Builder(applicationContext)
                    .bufferCapacity(3)
                    .requestsSizeForUpload(1)
                    .build()
            )
            .connectTimeout(5, TimeUnit.SECONDS)
            .build()
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
}