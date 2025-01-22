package com.agpr.cryptomarket.ui.detailCoin

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.agpr.cryptomarket.network.model.CoinApiModel
import com.agpr.cryptomarket.network.model.MarketApiModel
import com.agpr.cryptomarket.utils.toCurrency
import com.agpr.cryptomarket.utils.toMarketCap
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.DividerProperties
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.IndicatorPosition
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.Line

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailCoinScreen() {
    val chartInterval = listOf("M1", "M5", "M15", "M30", "H1", "H2", "H6", "H12", "D1")
    val viewModel = hiltViewModel<DetailCoinViewModel>()
    val detailCoinState = viewModel.detailCoinState
    val scrollState = rememberScrollState()
    val coinDetail = detailCoinState.coinDetail

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    if (coinDetail != null) {
                        Row {
                            AsyncImage(
                                model = "https://assets.coincap.io/assets/icons/${coinDetail.symbol.lowercase()}@2x.png",
                                contentDescription = coinDetail.symbol,
                                modifier = Modifier
                                    .height(32.dp)
                                    .width(32.dp)
                                    .padding(end = 8.dp)
                            )
                            Text(
                                text = coinDetail.name,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = coinDetail.symbol,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray
                            )
                        }
                    } else Box {}
                    Icon(
                        imageVector = if (detailCoinState.isFavorite) {
                            Icons.Filled.Favorite
                        } else {
                            Icons.Outlined.FavoriteBorder
                        },
                        contentDescription = "Favorite",
                        modifier = Modifier.clickable { viewModel.toggleFavorite() }
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = { /* TODO: Handle back action */ }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        )
    }) {
        Column(
            Modifier
                .verticalScroll(scrollState)
                .padding(it)
        ) {
            if (detailCoinState.loadingChart) {
                Box(modifier = Modifier.height(310.dp))
            } else {
                Box(Modifier.height(300.dp)) {
                    val chartColor =
                        if (detailCoinState.charts.last().priceUsd > detailCoinState.charts.first().priceUsd) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            Color.Red
                        }

                    LineChart(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(22.dp),
                        labelHelperProperties = LabelHelperProperties(false),
                        indicatorProperties = HorizontalIndicatorProperties(
                            textStyle = TextStyle(
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 12.sp,
                            ),
                            position = IndicatorPosition.Horizontal.End,
                            contentBuilder = {
                                it.toCurrency()
                            }
                        ),
                        data =
                        listOf(
                            Line(
                                label = "",
                                values = detailCoinState.charts.map { it.priceUsd },
                                color = SolidColor(chartColor),
                                firstGradientFillColor = chartColor.copy(alpha = .1f),
                                secondGradientFillColor = Color.Transparent,
                                strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                                gradientAnimationDelay = 1000,
                                drawStyle = DrawStyle.Stroke(width = 2.dp),
                            )
                        ),
                        gridProperties = GridProperties(false),
                        dividerProperties = DividerProperties(false),
                        animationMode = AnimationMode.Together(delayBuilder = {
                            it * 500L
                        }),
                        minValue = detailCoinState.minValue,
                        maxValue = detailCoinState.maxValue,
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                chartInterval.forEach {
                    val selected = detailCoinState.interval.lowercase() == it.lowercase()
                    val primaryColor = MaterialTheme.colorScheme.primary
                    Text(
                        it,
                        modifier = Modifier
                            .drawWithContent {
                                drawContent()
                                drawLine(
                                    color = if (selected) {
                                        primaryColor
                                    } else {
                                        Color.Transparent
                                    },
                                    start = Offset(0f, size.height),
                                    end = Offset(size.width, size.height),
                                    strokeWidth = 4F
                                )
                            }
                            .clickable {
                                viewModel.getChart(
                                    it
                                        .lowercase()
                                )
                            },
                        fontSize = 12.sp,
                        color = if (selected) {
                            primaryColor
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        }
                    )
                }
            }

            Box(modifier = Modifier.height(16.dp))
            CoinStatistics(detailCoinState.coinDetail)
            ListMarket(
                detailCoinState.listMarket,
                detailCoinState.loadingLoadMore
            ) { viewModel.getListMarket(detailCoinState.limit + 1) }
        }
    }
}

@Composable
fun ListMarket(data: List<MarketApiModel>, loadingLoadMore: Boolean, loadMore: () -> Unit) {
    if (data.isNotEmpty()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                "Available Markets",
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.Bold,
            )
            MarketRow(
                name = "Name",
                pair = "Pair",
                volume = "Volume (24Hr)",
                fontWeight = FontWeight.Bold,
            )
            data.map { item ->
                MarketRow(
                    name = item.exchangeId,
                    pair = "${item.baseSymbol}/${item.quoteSymbol}",
                    volume = item.volumeUsd24Hr.toMarketCap()
                )
            }
            Box(
                Modifier
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .fillMaxSize()
                    .clickable {
                        loadMore()
                    }
                    .padding(6.dp)

            ) {
                if (loadingLoadMore) {
                    CircularProgressIndicator(
                        Modifier
                            .align(Alignment.Center)
                            .size(16.dp), strokeWidth = 1.dp
                    )
                } else {
                    Text(
                        "Load More",
                        Modifier.align(Alignment.Center),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
    } else {
        Box {}
    }
}

@Composable
fun CoinStatistics(data: CoinApiModel?) {
    if (data != null) {
        Column(Modifier.height(280.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                "Coin Statistic",
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.Bold,
            )
            CoinInfoRow(key = "Rank", value = "${data.rank}")
            CoinInfoRow(key = "Market Cap", value = "$${data.marketCapUsd.toCurrency()}")
            CoinInfoRow(key = "VWAP (24Hr)", value = "$${data.vwap24Hr.toCurrency()}")
            CoinInfoRow(key = "Supply", value = data.supply.toCurrency())
            CoinInfoRow(key = "Volume (24Hr)", value = "$${data.volumeUsd24Hr.toCurrency()}")
            CoinInfoRow(
                key = "Change (24Hr)",
                value = "$ ${data.changePercent24Hr.toCurrency()}%",
                valueColor = if (data.changePercent24Hr > 0) {
                    Color.Green
                } else {
                    Color.Red
                }
            )
        }
    } else {
        Box {}
    }
}

@Composable
fun CoinInfoRow(key: String, value: String, valueColor: Color = Color.Unspecified) {
    Box(
        Modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = key, fontSize = 14.sp)
            Text(text = value, color = valueColor, fontSize = 14.sp)
        }
    }
}

@Composable
fun MarketRow(name: String, pair: String, volume: String, fontWeight: FontWeight? = null) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(name, Modifier.width(150.dp), fontWeight = fontWeight, fontSize = 14.sp)
        Text(
            pair,
            Modifier.width(100.dp),
            fontWeight = fontWeight,
            fontSize = 14.sp,
            textAlign = TextAlign.Right
        )
        Text(
            volume,
            Modifier.width(150.dp),
            fontWeight = fontWeight,
            fontSize = 14.sp,
            textAlign = TextAlign.Right
        )
    }
}
