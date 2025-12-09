package com.example.profesorsabio.ui.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.profesorsabio.data.SettingsManager

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val settingsManager = SettingsManager(application)

    fun saveApiKey(apiKey: String) {
        settingsManager.saveApiKey(apiKey)
    }

    fun getApiKey(): String {
        return settingsManager.getApiKey()
    }

    fun saveModel(model: String) {
        settingsManager.saveModel(model)
    }

    fun getModel(): String {
        return settingsManager.getModel()
    }
}