package com.example.profesorsabio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.profesorsabio.ui.chat.ChatScreen
import com.example.profesorsabio.ui.settings.SettingsScreen
import com.example.profesorsabio.ui.splash.SplashScreen
import com.example.profesorsabio.ui.theme.ProfesorSabioTheme
import com.example.profesorsabio.ui.video.VideoScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfesorSabioTheme {
                val navController = rememberNavController()
                var currentScreen by remember { mutableStateOf("splash") }

                when (currentScreen) {
                    "splash" -> SplashScreen { currentScreen = "video" }
                    "video" -> VideoScreen { currentScreen = "chat" }
                    "chat" -> {
                        NavHost(navController = navController, startDestination = "chat") {
                            composable("chat") { ChatScreen(onNavigateToSettings = { navController.navigate("settings") }) }
                            composable("settings") { SettingsScreen(onNavigateUp = { navController.popBackStack() }) }
                        }
                    }
                }
            }
        }
    }
}