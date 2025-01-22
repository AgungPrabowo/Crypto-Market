package com.agpr.cryptomarket.di

import com.agpr.cryptomarket.data.WebSocketClient
import com.agpr.cryptomarket.data.WebSocketClientMain
import com.agpr.cryptomarket.network.ExchangeApi
import com.agpr.cryptomarket.network.MarketApi
import com.agpr.cryptomarket.repository.ExchangeRepository
import com.agpr.cryptomarket.repository.MarketRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(WebSockets)
            defaultRequest {
                url("https://api.coincap.io/v2/")
                contentType(ContentType.Application.Json)
            }
            install(ContentNegotiation) {
                register(ContentType.Text.Html, KotlinxSerializationConverter(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }))
                json(Json {
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                })
            }
        }
    }

    @Provides
    @Singleton
    fun provideMarketService(httpClient: HttpClient): MarketApi =
        MarketRepository(httpClient)

    @Provides
    @Singleton
    fun provideRealtimeMarketService(httpClient: HttpClient): WebSocketClient =
        WebSocketClientMain(httpClient)

    @Provides
    @Singleton
    fun provideExchangeService(httpClient: HttpClient): ExchangeApi =
        ExchangeRepository(httpClient)
}
