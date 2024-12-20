package com.agpr.cryptomarket.ui.market

import com.agpr.cryptomarket.network.model.CoinApiModel

data class MarketState(
    val listCoin: List<CoinApiModel> = listOf()
)