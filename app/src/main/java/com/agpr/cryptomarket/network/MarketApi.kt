package com.agpr.cryptomarket.network

import com.agpr.cryptomarket.network.model.ListCoinApiModel
import com.agpr.cryptomarket.network.model.ResponseApi

interface MarketApi {
    suspend fun getListCoins(): ResponseApi<List<ListCoinApiModel>>
}