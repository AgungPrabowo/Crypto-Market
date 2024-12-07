package com.agpr.cryptomarket.network

import com.agpr.cryptomarket.network.model.ListExchangeApiModel
import com.agpr.cryptomarket.network.model.ResponseApi

interface ExchangeApi {
    suspend fun getListExchanges(): ResponseApi<List<ListExchangeApiModel>>
}