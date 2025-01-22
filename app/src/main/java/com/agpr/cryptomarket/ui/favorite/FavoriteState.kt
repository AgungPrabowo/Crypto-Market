package com.agpr.cryptomarket.ui.favorite

import com.agpr.cryptomarket.network.model.CoinApiModel

data class FavoriteState(
    val listCoin: List<CoinApiModel> = listOf(),
    val loading: Boolean = true
)