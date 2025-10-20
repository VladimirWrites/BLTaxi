package com.vlad1m1r.bltaxi.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

private const val BASE_URL = "https://raw.githubusercontent.com/VladimirWrites/BLTaxi/master/"

internal fun provideRetrofit(client: OkHttpClient): Retrofit {
    val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
}
