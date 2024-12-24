package com.agpr.cryptomarket.ui.exchange

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.agpr.cryptomarket.component.Loading
import com.agpr.cryptomarket.utils.toMarketCap

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExchangeScreen(navController: NavController) {
    val viewModel = hiltViewModel<ExchangeViewModel>()
    val exchangeState = viewModel.exchangeState

    if (exchangeState.listExchange.isNotEmpty()) {
        Scaffold(topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Exchange",
                        fontSize = 20.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            )
        }) {
            Column(Modifier.padding(it)) {
                Row(
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    Arrangement.SpaceBetween,
                ) {
                    Row {

                        Text(text = "#")
                        Box(modifier = Modifier.width(8.dp))
                        Text(text = "Name")
                    }
                    Text(text = "Volume (24Hr)")
                }
                LazyColumn {
                    itemsIndexed(
                        exchangeState.listExchange,
                        key = { _, item -> item.rank }) { index, item ->
                        val url =
                            item.exchangeUrl.replace(Regex("^https?://"), "").replace("/", "")
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable { navController.navigate("DetailExchange/$url") }
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth(),
                                Arrangement.SpaceBetween,
                                Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = item.rank)
                                    Box(modifier = Modifier.width(8.dp))
                                    Text(text = item.name)
                                }
                                Text(text = item.volumeUsd.toMarketCap())
                            }
                            if (index < exchangeState.listExchange.lastIndex)
                                HorizontalDivider(color = Color.Gray, thickness = .5.dp)
                        }
                    }
                }
            }
        }
    } else Loading()
}
