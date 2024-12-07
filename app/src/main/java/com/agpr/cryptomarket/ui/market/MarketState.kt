package com.example.myapplication.ui.market

import com.agpr.cryptomarket.network.model.ListCoinApiModel

data class MarketState(
    val listCoin: List<ListCoinApiModel> = listOf()
)