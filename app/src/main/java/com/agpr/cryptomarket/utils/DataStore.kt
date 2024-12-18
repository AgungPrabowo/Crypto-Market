package com.agpr.cryptomarket.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.core.DataStore
import javax.inject.Inject

class DataStore @Inject constructor(context: Context) {
    val dataStore = context.dataStore

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
        val IS_DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
        val IS_SECURE_ENV_KEY = booleanPreferencesKey("secure_env")
        val IS_BIOMETRIC_AUTH_SET_KEY = booleanPreferencesKey("biometric_auth")
    }
}