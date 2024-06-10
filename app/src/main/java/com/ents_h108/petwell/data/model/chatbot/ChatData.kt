package com.ents_h108.petwell.data.model.chatbot

import android.graphics.Bitmap
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.ResponseStoppedException
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.ents_h108.petwell.BuildConfig

object ChatData {
    private const val API_KEY = BuildConfig.GEMINI_API_KEY
    private const val MODEL_NAME = "gemini-1.5-flash"

    private val generativeModel = GenerativeModel(
        modelName = MODEL_NAME,
        apiKey = API_KEY
    )

    private suspend fun generateChatResponse(content: Any): Chat {
        return try {
            val response = withContext(Dispatchers.IO) {
                when (content) {
                    is String -> generativeModel.generateContent(content)
                    else -> generativeModel.generateContent(content as com.google.ai.client.generativeai.type.Content)
                }
            }
            Chat(
                prompt = response.text ?: "error",
                bitmap = null,
                isFromUser = false
            )
        } catch (e: ResponseStoppedException) {
            Chat(
                prompt = e.message ?: "error",
                bitmap = null,
                isFromUser = false
            )
        }
    }

    suspend fun getResponse(prompt: String): Chat {
        return generateChatResponse(prompt)
    }

    suspend fun getResponseWithImage(prompt: String, bitmap: Bitmap): Chat {
        val inputContent = content {
            image(bitmap)
            text(prompt)
        }
        return generateChatResponse(inputContent)
    }
}
