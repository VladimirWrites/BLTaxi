package com.vlad1m1r.bltaxi.di

import com.vlad1m1r.baseui.CoroutineDispatcherProvider
import com.vlad1m1r.bltaxi.AppInfoProviderImpl
import com.vlad1m1r.bltaxi.Navigator
import com.vlad1m1r.bltaxi.about.AppInfoProvider
import com.vlad1m1r.bltaxi.taxi.TaxiNavigator
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideTaxiNavigator(): TaxiNavigator {
        return Navigator()
    }

    @Provides
    fun provideCoroutineDispatcherProvider(): CoroutineDispatcherProvider {
        return CoroutineDispatcherProvider(main = Dispatchers.Main, io = Dispatchers.IO)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppInfoProviderModule {

    @Binds
    abstract fun bindAppInfoProvider(
        appInfoProviderImpl: AppInfoProviderImpl
    ): AppInfoProvider
}

