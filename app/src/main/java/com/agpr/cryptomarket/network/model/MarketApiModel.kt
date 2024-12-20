package com.agpr.cryptomarket.network.model

import kotlinx.serialization.Serializable

@Serializable
data class MarketApiModel(
    val exchangeId: String,
    val baseId: String,
    val quoteId: String,
    val baseSymbol: String,
    val quoteSymbol: String,
    val volumeUsd24Hr: Double,
    val priceUsd: Double,
    val volumePercent: Double
)
