package com.agpr.cryptomarket.ui.detailExchange

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailExchangeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val url: String? = savedStateHandle["url"]
    var uiState by mutableStateOf(DetailExchangeState())
        private set

    init {
        url?.let {
            viewModelScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    uiState = uiState.copy(url = it)
                }
            }
        }
    }
}