package com.agpr.cryptomarket.repository

import com.agpr.cryptomarket.network.ExchangeApi
import com.agpr.cryptomarket.network.model.ListExchangeApiModel
import com.agpr.cryptomarket.network.model.ResponseApi
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path

class ExchangeRepository(
    private val client: HttpClient
) : ExchangeApi {
    override suspend fun getListExchanges(): ResponseApi<List<ListExchangeApiModel>> {
        return client.get {
            url {
                path("exchanges")
            }
        }.body()
    }
}