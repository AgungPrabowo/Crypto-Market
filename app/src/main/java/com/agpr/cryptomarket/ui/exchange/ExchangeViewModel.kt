package com.agpr.cryptomarket.ui.exchange

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agpr.cryptomarket.network.ExchangeApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val exchangeApi: ExchangeApi
) : ViewModel() {
    var exchangeState by mutableStateOf(ExchangeState())
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val result = exchangeApi.getListExchanges()
            withContext(Dispatchers.Main) {
                exchangeState = exchangeState.copy(listExchange = result.data)
            }
        }
    }
}
