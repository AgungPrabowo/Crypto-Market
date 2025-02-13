package com.agpr.cryptomarket.ui.favorite

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agpr.cryptomarket.network.MarketApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val marketApi: MarketApi
) : ViewModel() {
    var marketState by mutableStateOf(FavoriteState())
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val result = marketApi.getListCoins()
            withContext(Dispatchers.Main) {
                marketState = marketState.copy(listCoin = result.data, loading = false)
            }
        }
    }
}