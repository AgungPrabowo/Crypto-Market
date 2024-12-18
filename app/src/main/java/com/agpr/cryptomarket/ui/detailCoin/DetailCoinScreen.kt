package com.agpr.cryptomarket.ui.detailCoin

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.agpr.cryptomarket.utils.toCurrency
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.DividerProperties
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.IndicatorPosition
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.Line

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailCoinScreen() {
    val chartInterval = listOf("M1", "M5", "M15", "M30", "H1", "H2", "H6", "H12", "D1")
    val viewModel = hiltViewModel<DetailCoinViewModel>()
    val detailCoinState = viewModel.detailCoinState

    Column {
        if (detailCoinState.loadingChart) {
            Box(modifier = Modifier.height(310.dp))
        } else {
            Column {
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
            }
        }
    }
}
