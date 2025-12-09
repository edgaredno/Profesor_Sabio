package com.example.profesorsabio.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = viewModel(),
    onNavigateUp: () -> Unit
) {
    var apiKey by remember { mutableStateOf(viewModel.getApiKey()) }
    var model by remember { mutableStateOf(viewModel.getModel()) }
    var expanded by remember { mutableStateOf(false) }
    val models = listOf("gemini-1.5-flash", "gemini-1.5-pro")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configuración") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(
                value = apiKey,
                onValueChange = { apiKey = it },
                label = { Text("Clave de API de Gemini") },
                modifier = Modifier.fillMaxWidth()
            )
            ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                TextField(
                    value = model,
                    onValueChange = {},
                    label = { Text("Modelo") },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    models.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                model = selectionOption
                                expanded = false
                            }
                        )
                    }
                }
            }
            Button(
                onClick = {
                    viewModel.saveApiKey(apiKey)
                    viewModel.saveModel(model)
                    onNavigateUp()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar")
            }
        }
    }
}