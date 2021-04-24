package com.vlad1m1r.bltaxi.remote.di

import com.vlad1m1r.bltaxi.remote.*
import dagger.Binds
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
object RetrofitModule {

    @Provides
    internal fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) addInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            )
        }.build()
    }

    @Provides
    internal fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideTaxiService(
        retrofit: Retrofit
    ): TaxiService {
        return getTaxiService(retrofit)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteModule {

    @Binds
    abstract fun bindTaxiProviderRemote(
        taxiProviderRemoteImpl: TaxiProviderRemoteImpl
    ): TaxiProviderRemote
}
