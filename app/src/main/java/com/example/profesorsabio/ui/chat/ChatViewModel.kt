package com.example.profesorsabio.ui.chat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.profesorsabio.data.SettingsManager
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException

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
            systemInstruction = content { text(systemInstruction) }
        )
    }

    fun sendMessage(message: String) {
        val userMessage = Message(author = "user", text = message)
        _uiState.update {
            it.copy(
                messages = it.messages + userMessage,
                suggestions = emptyList()
            )
        }

        viewModelScope.launch {
            try {
                val chatModel = getGenerativeModel("Eres el 'Profesor Sabio', un búho muy inteligente, paciente y amigable, diseñado para interactuar con niños de primaria. Tu objetivo es responder a sus preguntas sobre cualquier tema académico o de interés general. **Todas tus respuestas deben ser educativas, alentadoras y estar adaptadas a un nivel de comprensión de un niño de 6 a 12 años.** Utiliza un tono lúdico y motivador. Comienza cada conversación con un saludo que incluya tu característico 'Hoo-hoo'.")

                val inputContent = content(role = "user") { text(message) }
                val response = chatModel.generateContent(inputContent)

                response.text?.let { modelResponseText ->
                    val modelMessage = Message(author = "model", text = modelResponseText)
                    _uiState.update { it.copy(messages = it.messages + modelMessage) }
                    generateSuggestions(modelResponseText)
                }

            } catch (e: Exception) {
                // Captura genérica de excepciones
                val errorMessage = when (e) {
                    is ServerException -> "El servidor de Gemini no está disponible. Inténtalo más tarde."
                    is CancellationException -> throw e // Propagar CancellationException si es necesario
                    else -> "No se pudo conectar. Revisa tu conexión a internet o la clave de API."
                }
                _uiState.update { it.copy(error = errorMessage) }
            }
        }
    }

    private fun generateSuggestions(topic: String) {
        viewModelScope.launch {
            try {
                val suggestionModel = getGenerativeModel("Genera 3 preguntas cortas que un niño de primaria podría hacer a continuación sobre el tema dado. Responde solo con las preguntas, cada una en una nueva línea, sin numeración ni guiones.")
                if (suggestionModel == null) return@launch 

                val topicContent = content { text(topic) }
                val response = suggestionModel.generateContent(topicContent)
                val suggestions = response.text?.split("\n")?.filter { it.isNotBlank() } ?: emptyList()
                _uiState.update { it.copy(suggestions = suggestions) }
            } catch (e: Exception) {
                // Suggestion generation failure is not critical, so we can ignore it or log it.
            }
        }
    }

    fun clearChat() {
        _uiState.value = ChatUiState()
    }
    
    fun errorShown() {
        _uiState.update { it.copy(error = null) }
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
