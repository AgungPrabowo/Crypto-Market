package com.agpr.cryptomarket.ui.detailCoin

import com.agpr.cryptomarket.network.model.ChartCoinApiModel

data class DetailCoinState(
    val interval: String = "m1",
    val charts: List<ChartCoinApiModel> = listOf(),
    val loadingChart: Boolean = true,
    val minValue: Double = 0.0,
    val maxValue: Double = 0.0,
)
