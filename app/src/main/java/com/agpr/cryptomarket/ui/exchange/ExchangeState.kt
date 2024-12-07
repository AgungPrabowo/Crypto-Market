package com.agpr.cryptomarket.ui.exchange

import com.agpr.cryptomarket.network.model.ListExchangeApiModel

data class ExchangeState (
    val listExchange: List<ListExchangeApiModel> = listOf()
)