package com.agpr.cryptomarket.data

import kotlinx.serialization.Serializable

@Serializable
data class WebSocketEvent(val data: String)
