package com.example.profesorsabio.ui.chat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.profesorsabio.data.SettingsManager
import com.google.ai.client.generativeai.GenerativeModel
// 1. Importa la función 'content' para construir objetos de contenido
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(application: Application) : AndroidViewModel(application) {

    private val settingsManager = SettingsManager(application)

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState = _uiState.asStateFlow()

    private fun getGenerativeModel(systemInstruction: String): GenerativeModel {
        val apiKey = settingsManager.getApiKey()
        val model = settingsManager.getModel()
        return GenerativeModel(
            modelName = model,
            apiKey = apiKey,
            // 2. Convierte el String de la instrucción del sistema a un objeto Content
            systemInstruction = content { text(systemInstruction) }
        )
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            val userMessage = Message(author = "user", text = message)
            _uiState.value = _uiState.value.copy(
                messages = _uiState.value.messages + userMessage,
                suggestions = emptyList() // Clear suggestions while waiting for new ones
            )

            try {
                val chatModel = getGenerativeModel("Eres el 'Profesor Sabio', un búho muy inteligente, paciente y amigable, diseñado para interactuar con niños de primaria. Tu objetivo es responder a sus preguntas sobre cualquier tema académico o de interés general. **Todas tus respuestas deben ser educativas, alentadoras y estar adaptadas a un nivel de comprensión de un niño de 6 a 12 años.** Utiliza un tono lúdico y motivador. Comienza cada conversación con un saludo que incluya tu característico 'Hoo-hoo'.")

                // 3. Convierte el String del mensaje del usuario a un objeto Content
                val inputContent = content(role = "user") { text(message) }
                val response = chatModel.generateContent(inputContent)

                response.text?.let { modelResponseText ->
                    val modelMessage = Message(author = "model", text = modelResponseText)
                    _uiState.value = _uiState.value.copy(messages = _uiState.value.messages + modelMessage)
                    generateSuggestions(modelResponseText)
                }

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "¡Hoo-hoo! Parece que mi conexión está un poco nublada. Inténtalo de nuevo."
                )
            }
        }
    }

    private fun generateSuggestions(topic: String) {
        viewModelScope.launch {
            try {
                val suggestionModel = getGenerativeModel("Genera 3 preguntas cortas que un niño de primaria podría hacer a continuación sobre el tema dado. Responde solo con las preguntas, cada una en una nueva línea, sin numeración ni guiones.")

                // 4. Haz lo mismo para la generación de sugerencias
                val topicContent = content { text(topic) }
                val response = suggestionModel.generateContent(topicContent)
                val suggestions = response.text?.split("\n")?.filter { it.isNotBlank() } ?: emptyList()
                _uiState.value = _uiState.value.copy(suggestions = suggestions)
            } catch (e: Exception) {
                // Suggestion generation failure is not critical, so we can ignore it or log it.
            }
        }
    }
}

data class ChatUiState(
    val messages: List<Message> = emptyList(),
    val suggestions: List<String> = emptyList(),
    val error: String? = null
)

data class Message(
    val author: String,
    val text: String
)

