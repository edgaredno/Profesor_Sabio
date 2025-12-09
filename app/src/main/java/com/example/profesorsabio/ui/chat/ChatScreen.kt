package com.example.profesorsabio.ui.chat

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    onNavigateToSettings: () -> Unit,
    factory: ChatViewModelFactory = ChatViewModelFactory(LocalContext.current.applicationContext as Application)
) {
    val viewModel: ChatViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsState()
    var message by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profesor Sabio") },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.messages) { msg ->
                    ChatMessage(message = msg)
                }
            }

            if (uiState.suggestions.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    uiState.suggestions.forEach { suggestion ->
                        Button(onClick = { viewModel.sendMessage(suggestion) }) {
                            Text(suggestion)
                        }
                    }
                }
            }

            uiState.error?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(16.dp))
            }

            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = message,
                    onValueChange = { message = it },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { 
                    viewModel.sendMessage(message)
                    message = ""
                }) {
                    Text("Enviar")
                }
            }
        }
    }
}