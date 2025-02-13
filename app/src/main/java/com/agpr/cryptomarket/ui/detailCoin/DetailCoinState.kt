package com.agpr.cryptomarket.ui.detailCoin

import com.agpr.cryptomarket.network.model.ChartCoinApiModel
import com.agpr.cryptomarket.network.model.CoinApiModel
import com.agpr.cryptomarket.network.model.MarketApiModel

data class DetailCoinState(
    val interval: String = "m1",
    val charts: List<ChartCoinApiModel> = listOf(),
    val loadingChart: Boolean = true,
    val minValue: Double = 0.0,
    val maxValue: Double = 0.0,
    val coinDetail: CoinApiModel? = null,
    val listMarket: List<MarketApiModel> = listOf(),
    val offset: Int = 1,
    val limit: Int = 10,
    val loadingLoadMore: Boolean = false,
    val isFavorite: Boolean = false,
)
