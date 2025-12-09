package com.example.profesorsabio.data

import android.content.Context
import android.content.SharedPreferences

class SettingsManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("profesor_sabio_prefs", Context.MODE_PRIVATE)

    fun saveApiKey(apiKey: String) {
        prefs.edit().putString("gemini_api_key", apiKey).apply()
    }

    fun getApiKey(): String {
        return prefs.getString("gemini_api_key", "AIzaSyBLS2DwwPx0-boa2I3Elku9dOaHbTz1yQ0") ?: ""
    }

    fun saveModel(model: String) {
        prefs.edit().putString("gemini_model", model).apply()
    }

    fun getModel(): String {
        return prefs.getString("gemini_model", "gemini-1.5-flash") ?: "gemini-1.5-flash"
    }
}