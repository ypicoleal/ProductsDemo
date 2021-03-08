package com.example.infrastructure.network

import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitBuilder(
        private val baseUrl: String
) {
    private val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    fun retrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(createClient())
                .build()
    }

    private fun createClient(): OkHttpClient {
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1

        return okHttpBuilder()
                .dispatcher(dispatcher)
                .addInterceptor(logging)
                .build()
    }

    private fun okHttpBuilder(): OkHttpClient.Builder {
        return OkHttpClient().newBuilder()
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
    }

    companion object {
        private const val TIME_OUT = 60L
    }
}