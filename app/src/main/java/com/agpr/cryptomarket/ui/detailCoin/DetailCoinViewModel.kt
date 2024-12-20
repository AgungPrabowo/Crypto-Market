package com.agpr.cryptomarket.ui.detailCoin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agpr.cryptomarket.network.MarketApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailCoinViewModel @Inject constructor(
    private val marketApi: MarketApi,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val coin: String? = savedStateHandle["coin"]
    var detailCoinState by mutableStateOf(DetailCoinState())
        private set

    init {
        coin?.let {
            viewModelScope.launch(Dispatchers.IO) {
                getCoinById()
                getListMarket()
                val result =
                    marketApi.getChartCoin(coin = coin, interval = "m1")
                val minValue = result.data.minBy { it.priceUsd }.priceUsd
                val maxValue = result.data.maxBy { it.priceUsd }.priceUsd
                withContext(Dispatchers.Main) {
                    detailCoinState = detailCoinState.copy(
                        charts = result.data,
                        minValue = minValue,
                        maxValue = maxValue,
                        loadingChart = false,
                    )
                }
            }
        }
    }

    fun getChart(interval: String) {
        if (coin != null) {
            viewModelScope.launch {
                detailCoinState = detailCoinState.copy(loadingChart = true)
                val result = marketApi.getChartCoin(coin = coin, interval = interval)
                val minValue = result.data.minBy { it.priceUsd }.priceUsd
                val maxValue = result.data.maxBy { it.priceUsd }.priceUsd
                detailCoinState = detailCoinState.copy(
                    charts = result.data,
                    interval = interval,
                    loadingChart = false,
                    minValue = minValue,
                    maxValue = maxValue,
                )
            }
        }
    }

    private fun getCoinById() {
        if (coin != null) {
            viewModelScope.launch {
                val result = marketApi.getCoinById(coin)
                detailCoinState = detailCoinState.copy(coinDetail = result.data)
            }
        }
    }

    fun getListMarket(offset: Int = 1, loadMore: Boolean = true) {
        if (coin != null) {
            viewModelScope.launch {
                detailCoinState = detailCoinState.copy(loadingLoadMore = true)
                val result = marketApi.getListMarket(
                    coin, if (loadMore) {
                        offset * detailCoinState.offset
                    } else {
                        offset
                    }
                )
                detailCoinState = if (loadMore) {
                    detailCoinState.copy(
                        listMarket = detailCoinState.listMarket + result.data,
                        offset = offset,
                        loadingLoadMore = false,
                    )
                } else {
                    detailCoinState.copy(listMarket = result.data, loadingLoadMore = false)
                }
            }
        }
    }
}