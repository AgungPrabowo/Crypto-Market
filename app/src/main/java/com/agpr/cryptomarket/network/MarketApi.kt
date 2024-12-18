package com.agpr.cryptomarket.network

import com.agpr.cryptomarket.network.model.ChartCoinApiModel
import com.agpr.cryptomarket.network.model.ListCoinApiModel
import com.agpr.cryptomarket.network.model.ResponseApi

interface MarketApi {
    suspend fun getListCoins(): ResponseApi<List<ListCoinApiModel>>
    suspend fun getChartCoin(coin: String, interval: String): ResponseApi<List<ChartCoinApiModel>>
}