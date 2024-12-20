package com.agpr.cryptomarket.repository

import com.agpr.cryptomarket.network.MarketApi
import com.agpr.cryptomarket.network.model.ChartCoinApiModel
import com.agpr.cryptomarket.network.model.CoinApiModel
import com.agpr.cryptomarket.network.model.MarketApiModel
import com.agpr.cryptomarket.network.model.ResponseApi
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path

class MarketRepository(
    private val client: HttpClient
) : MarketApi {
    override suspend fun getListCoins(): ResponseApi<List<CoinApiModel>> {
        return client.get {
            url {
                path("assets")
            }
        }.body()
    }

    override suspend fun getChartCoin(
        coin: String,
        interval: String
    ): ResponseApi<List<ChartCoinApiModel>> {
        return client.get {
            url {
                path("assets/$coin/history")
                parameters.append("interval", interval)
            }
        }.body()
    }

    override suspend fun getCoinById(coin: String): ResponseApi<CoinApiModel> {
        return client.get {
            url {
                path("assets/$coin")
            }
        }.body()
    }

    override suspend fun getListMarket(
        coin: String,
        offset: Int,
        limit: Int
    ): ResponseApi<List<MarketApiModel>> {
        return client.get {
            url {
                path("assets/$coin/markets")
                parameters.append("offset", "$offset")
                parameters.append("limit", "$limit")
            }
        }.body()
    }

}