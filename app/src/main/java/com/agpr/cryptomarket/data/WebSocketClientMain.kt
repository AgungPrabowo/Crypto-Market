package com.agpr.cryptomarket.data

import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.serialization.json.Json

class WebSocketClientMain(
    private val client: HttpClient
) : WebSocketClient {
    private var session: WebSocketSession? = null

    override fun getPriceStream(): Flow<Map<String, String>> {
        return flow {
            session = client.webSocketSession {
                url("wss://ws.coincap.io/prices?assets=ALL")
            }

            val state = session!!
                .incoming
                .consumeAsFlow()
                .filterIsInstance<Frame.Text>()
                .mapNotNull {
                    Json.decodeFromString<Map<String, String>>(it.readText())
                }

            emitAll(state)
        }
    }

    override suspend fun sendAction(action: WebSocketEvent) {
//        session?.outgoing?.send(
//            Frame.Text(action)
//        )
    }

    override suspend fun close() {
        session?.close()
        session = null
    }
}