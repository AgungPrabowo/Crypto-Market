package com.agpr.cryptomarket.di

import android.content.Context
import com.agpr.cryptomarket.utils.DataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore = DataStore(context)

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context = context
}