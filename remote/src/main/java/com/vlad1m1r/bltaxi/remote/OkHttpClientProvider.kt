package com.vlad1m1r.bltaxi.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) addInterceptor(loggingInterceptor)
    }.build()
}