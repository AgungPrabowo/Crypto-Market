package com.agpr.cryptomarket.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ResponseApi<T> (
    val data: T,
    val timestamp: Long
)