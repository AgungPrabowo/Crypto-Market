package com.agpr.cryptomarket.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ListCoinApiModel(
    val id: String,
    val name: String,
    val symbol: String,
    val rank: Int,
    val supply: Double,
    val maxSupply: Double?,
    val marketCapUsd: Double,
    val volumeUsd24Hr: Double,
    val priceUsd: Double,
    val changePercent24Hr: Double,
    val vwap24Hr: Double,
    val explorer: String?,
    val up: Boolean = true
)