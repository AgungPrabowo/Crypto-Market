package com.agpr.cryptomarket.ui.detailExchange

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.kevinnzou.web.WebView
import com.kevinnzou.web.rememberWebViewState

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun DetailExchange() {
    val viewModel = hiltViewModel<DetailExchangeViewModel>()
    val state = rememberWebViewState(url = viewModel.uiState.url)

    WebView(state = state)
}