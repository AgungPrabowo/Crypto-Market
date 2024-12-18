package com.agpr.cryptomarket.ui.market

import com.agpr.cryptomarket.network.model.ListCoinApiModel

data class MarketState(
    val listCoin: List<ListCoinApiModel> = listOf()
)