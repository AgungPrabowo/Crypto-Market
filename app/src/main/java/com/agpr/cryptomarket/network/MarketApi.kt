package com.agpr.cryptomarket.network

import com.agpr.cryptomarket.network.model.ChartCoinApiModel
import com.agpr.cryptomarket.network.model.CoinApiModel
import com.agpr.cryptomarket.network.model.MarketApiModel
import com.agpr.cryptomarket.network.model.ResponseApi

interface MarketApi {
    suspend fun getListCoins(): ResponseApi<List<CoinApiModel>>
    suspend fun getChartCoin(coin: String, interval: String): ResponseApi<List<ChartCoinApiModel>>
    suspend fun getCoinById(coin: String): ResponseApi<CoinApiModel>
    suspend fun getListMarket(
        coin: String,
        offset: Int = 1,
        limit: Int = 10
    ): ResponseApi<List<MarketApiModel>>
}