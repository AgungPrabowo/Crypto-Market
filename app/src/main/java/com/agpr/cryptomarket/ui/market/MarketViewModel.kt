package com.example.myapplication.ui.market

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agpr.cryptomarket.data.WebSocketClient
import com.agpr.cryptomarket.network.MarketApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val marketApi: MarketApi,
    private val webSocketClient: WebSocketClient,
) : ViewModel() {
    var marketState by mutableStateOf(MarketState())
        private set

    val state = webSocketClient
        .getPriceStream()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), mapOf())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val result = marketApi.getListCoins()
            withContext(Dispatchers.Main) {
                marketState = marketState.copy(listCoin = result.data)
            }
        }
    }

    fun getPriceCoin(): StateFlow<Map<String, String>> {
        return webSocketClient.getPriceStream()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                hashMapOf()
            )
    }

    fun getPriceByCoin(coinList: Map<String, String>) {
        val result = marketState.listCoin.map {
            if (coinList.containsKey(it.id)) {
                val newPrice = coinList.getValue(it.id).toDouble()
                it.copy(priceUsd = newPrice, up = newPrice > it.priceUsd)
            } else {
                it
            }
        }
        marketState = marketState.copy(listCoin = result)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            webSocketClient.close()
        }
    }
}