package com.agpr.cryptomarket.network.model

import com.agpr.cryptomarket.utils.DateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class ChartCoinApiModel(
    val priceUsd: Double,
    val time: Long,
    @Serializable(with = DateSerializer::class)
    val date: LocalDateTime,
)
