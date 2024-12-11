package com.agpr.cryptomarket.ui.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.agpr.cryptomarket.R
import com.agpr.cryptomarket.component.SwitchCard
import com.agpr.cryptomarket.viewModel.ThemeViewModel

@Composable
fun SettingScreen() {
    val themeViewModel: ThemeViewModel = hiltViewModel()
    val themeState by themeViewModel.themeState.collectAsState()

    SwitchCard(
        text = "Dark Mode",
        icon = R.drawable.moon_icon,
        isChecked = themeState.isDarkMode,
        onCheckedChange = {
            themeViewModel.toggleTheme()
        }
    )
}