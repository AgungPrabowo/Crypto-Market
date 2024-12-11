package com.agpr.cryptomarket.viewModel

import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agpr.cryptomarket.utils.DataStore
import com.agpr.cryptomarket.utils.DataStore.Companion.IS_DARK_MODE_KEY
import com.agpr.cryptomarket.utils.DataStore.Companion.IS_SECURE_ENV_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ThemeState(val isDarkMode: Boolean, val isSecureEnv: Boolean)

@HiltViewModel
class ThemeViewModel @Inject constructor(dataStoreUtil: DataStore) : ViewModel() {
    private val _themeState = MutableStateFlow(ThemeState(isDarkMode = false, isSecureEnv = false))
    val themeState: StateFlow<ThemeState> = _themeState
    private val dataStore = dataStoreUtil.dataStore

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.data.map { preferences ->
                ThemeState(
                    isDarkMode = preferences[IS_DARK_MODE_KEY] ?: false,
                    isSecureEnv = preferences[IS_SECURE_ENV_KEY] ?: false
                )
            }.collect {
                _themeState.value = it
            }
        }
    }

    fun toggleTheme() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences[IS_DARK_MODE_KEY] = !(preferences[IS_DARK_MODE_KEY] ?: false)
            }
        }
    }
}