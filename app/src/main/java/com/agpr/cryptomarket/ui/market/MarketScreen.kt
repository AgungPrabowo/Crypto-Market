package com.agpr.cryptomarket.ui.market

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.agpr.cryptomarket.component.Loading
import com.agpr.cryptomarket.utils.toCurrency
import com.agpr.cryptomarket.utils.toMarketCap

@Composable
fun MarketScreen(navController: NavController) {
    val viewModel = hiltViewModel<MarketViewModel>()
    val marketState = viewModel.marketState
    val state by viewModel.state.collectAsState()

    viewModel.getPriceByCoin(state)

    if (marketState.listCoin.isNotEmpty()) {
        Column {
            Row(
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                Arrangement.SpaceBetween,
            ) {
                Row {
                    Text(text = "#")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Market Cap")
                }
                Text(text = "Price")
                Text(text = "24h %")
            }
            LazyColumn {
                itemsIndexed(marketState.listCoin, key = { _, item -> item.id }) { index, item ->
                    val infiniteTransition = rememberInfiniteTransition(label = item.id)
                    val blinking = infiniteTransition.animateColor(
                        if (item.up) Color.Green else Color.Red, LocalContentColor.current,
                        animationSpec = infiniteRepeatable(
                            animation = keyframes {
                                durationMillis = 1000
                            }
                        ), label = item.id
                    ).value

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { navController.navigate("DetailCoinScreen/${item.id}") }
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            Arrangement.SpaceBetween,
                            Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "${index + 1}")
                                Spacer(modifier = Modifier.width(8.dp))
                                AsyncImage(
                                    model = "https://assets.coincap.io/assets/icons/${item.symbol.lowercase()}@2x.png",
                                    contentDescription = item.symbol,
                                    modifier = Modifier
                                        .height(56.dp)
                                        .width(56.dp)
                                        .padding(end = 8.dp)
                                )
                                Column {
                                    Text(text = item.symbol, fontSize = 16.sp)
                                    Text(
                                        text = item.marketCapUsd.toMarketCap(),
                                        fontSize = 14.sp,
                                        color = Color(0xFF9096A2)
                                    )
                                }
                            }
                            Text(text = "$".plus(item.priceUsd.toCurrency()), color = blinking)
                            Text(
                                text = item.changePercent24Hr.toCurrency().plus("%"),
                                fontSize = 14.sp,
                                color = if (item.changePercent24Hr < 0)
                                    Color.Red else Color.Green
                            )
                        }
                    }
                }
            }
        }
    } else Loading()
}
