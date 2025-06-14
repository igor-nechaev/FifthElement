package com.nech9ev.fifthelement.internal.data

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

internal object NetworkModule {

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        coerceInputValues = true
        isLenient = true
    }

    private val httpClient = HttpClient(OkHttp) {
        engine {
            preconfigured = provideOkHttpClient()
            config {
                retryOnConnectionFailure(true)
            }
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer(provideJson())
        }

        install(HttpTimeout)
    }

    private val okHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(3, TimeUnit.SECONDS)
            .writeTimeout(3, TimeUnit.SECONDS)
            .build()

    internal fun provideHttpClient() = httpClient

    internal fun provideOkHttpClient() = okHttpClient

    private fun provideJson() = json
}
