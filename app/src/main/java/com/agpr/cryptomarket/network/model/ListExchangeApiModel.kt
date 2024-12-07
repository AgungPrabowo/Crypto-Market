package com.agpr.cryptomarket.network.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@Serializable
data class ListExchangeApiModel @OptIn(ExperimentalSerializationApi::class) constructor(
    val name: String,
    val rank: String,
    val percentTotalVolume: Double?,
    val volumeUsd: Double?,
    val tradingPairs: String,
    val exchangeUrl: String,
    val updated: Double
)
