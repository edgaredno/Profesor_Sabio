# Profesor Sabio: Chatbot Educativo para Niños

## Descripción del Proyecto

**Profesor Sabio** es una aplicación móvil nativa para Android diseñada como un **chatbot educativo** dirigido a niños de primaria.

El objetivo principal es ofrecer una experiencia de **aprendizaje interactivo y lúdico** mediante una interfaz conversacional amigable. La aplicación utiliza la **Inteligencia Artificial Gemini** para generar respuestas adaptadas al nivel de comprensión de los niños. Un personaje, un búho sabio, guía al estudiante, fomentando la curiosidad, la autonomía y el aprendizaje autodirigido.

## Características Principales

Basado en la estructura del código, estas son las funcionalidades clave implementadas:

*   **Chat Interactivo:** Interfaz principal de conversación con el Profesor Sabio (IA Gemini).
*   **Gestión de Sesión:** Manejo de la configuración y ajustes de la aplicación (`SettingsManager.kt`).
*   **Pantalla de Bienvenida (Splash):** Presentación inicial de la aplicación.
*   **Módulo de Video:** Posiblemente para tutoriales o contenido educativo complementario (`VideoScreen.kt`).
*   **Diseño Moderno:** Interfaz de usuario construida con **Jetpack Compose** para una experiencia nativa y fluida en Android.

## Tecnologías Utilizadas

| Categoría | Tecnología | Propósito |
| :--- | :--- | :--- |
| **Plataforma** | Android Nativo | Desarrollo de la aplicación móvil. |
| **Lenguaje** | Kotlin | Lenguaje de programación principal. |
| **UI/UX** | Jetpack Compose | Framework moderno para la construcción de la interfaz de usuario. |
| **Inteligencia Artificial** | Google Gemini API | Motor del chatbot para generar respuestas educativas. |
| **Gestión de Dependencias** | Gradle (Kotlin DSL) | Sistema de construcción del proyecto. |

## Historial de Desarrollo

Este proyecto se desarrolló en el contexto de un curso, con un enfoque en la integración de la IA y la construcción de una interfaz de usuario moderna.

| Fecha | Hito Principal | Descripción y Archivos Clave |
| :--- | :--- | :--- |
| **9 de Diciembre de 2025** | **Fase Inicial y Estructura** | Creación del repositorio y configuración inicial del proyecto Android. Se establecen los módulos base de la aplicación. |
| **9 de Diciembre de 2025** | **Desarrollo de la Interfaz de Usuario** | Implementación de las pantallas principales utilizando Jetpack Compose: `SplashScreen.kt`, `ChatScreen.kt`, `SettingsScreen.kt`, y `VideoScreen.kt`. Se define la estructura de datos para los mensajes (`ChatMessage.kt`). |
| **9 de Diciembre de 2025** | **Integración de la IA y Funcionalidad** | Conexión exitosa con la **API de Gemini**. Se implementa la lógica del `ChatViewModel.kt` y el gestor de configuración (`SettingsManager.kt`) para hacer la aplicación completamente funcional como chatbot. |
| **9 de Diciembre de 2025** | **Documentación Inicial** | Creación y primera actualización del archivo `README.md` con la descripción del proyecto y la confirmación de la funcionalidad de la API. |

## Instalación y Ejecución

Para ejecutar este proyecto localmente, necesitarás tener instalado Android Studio y el SDK de Android.

1.  **Clonar el Repositorio:**
    ```bash
    git clone https://github.com/edgaredno/Profesor_Sabio.git
    cd Profesor_Sabio
    ```
2.  **Configurar la API Key:**
    *   Asegúrate de obtener una clave de la API de Google Gemini.
    *   La clave debe ser configurada en el proyecto, probablemente en un archivo de propiedades o en el `SettingsManager.kt` (revisa el código para la ubicación exacta).
3.  **Abrir en Android Studio:**
    *   Abre el proyecto en Android Studio.
    *   Sincroniza Gradle.
4.  **Ejecutar:**
    *   Ejecuta la aplicación en un emulador o dispositivo Android.

  
