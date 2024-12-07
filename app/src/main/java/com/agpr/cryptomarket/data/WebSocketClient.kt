package com.agpr.cryptomarket.data

import kotlinx.coroutines.flow.Flow

interface WebSocketClient {
    fun getPriceStream(): Flow<Map<String, String>>
    suspend fun sendAction(action: WebSocketEvent)
    suspend fun close()
}