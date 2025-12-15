package com.example.profesorsabio.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = viewModel(),
    onNavigateUp: () -> Unit
) {
    var apiKey by remember { mutableStateOf("") } // Inicialmente vacío para no mostrar la clave guardada
    // Definir los modelos 2.5 disponibles
    val available25Models = listOf("gemini-2.5-flash", "gemini-2.5-pro", "gemini-2.5-turbo")
    
    // Obtener el modelo guardado, si no es un modelo 2.5 válido, establecer gemini-2.5-flash por defecto
    var model by remember { mutableStateOf(viewModel.getModel().takeIf { it in available25Models } ?: "gemini-2.5-flash") }
    
    var expanded by remember { mutableStateOf(false) }

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
            OutlinedTextField(
                value = apiKey,
                onValueChange = { apiKey = it },
                label = { Text("Clave de API de Gemini") },
                visualTransformation = PasswordVisualTransformation(), // Ocultar la clave
                modifier = Modifier.fillMaxWidth()
            )
            ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                OutlinedTextField(
                    value = model,
                    onValueChange = {},
                    label = { Text("Modelo") },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    available25Models.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                model = selectionOption
                                expanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }
            Button(
                onClick = {
                    // Solo guardar la clave si se ha introducido algo
                    if (apiKey.isNotBlank()) {
                        viewModel.saveApiKey(apiKey)
                    }
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