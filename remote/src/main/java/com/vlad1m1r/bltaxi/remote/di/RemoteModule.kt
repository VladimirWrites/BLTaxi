package com.vlad1m1r.bltaxi.remote.di

import com.vlad1m1r.bltaxi.remote.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://raw.githubusercontent.com/VladimirWrites/BLTaxi/master/"

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    fun bindTaxiProviderRemote(): TaxiProviderRemote {
        val okHttpClient = OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) addInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            )
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val taxiService = getTaxiService(retrofit)

        return TaxiProviderRemoteImpl(taxiService)
    }
}
