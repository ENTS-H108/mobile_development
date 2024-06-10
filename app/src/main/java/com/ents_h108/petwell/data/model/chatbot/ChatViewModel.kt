package com.ents_h108.petwell.data.model.chatbot

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val _chatState = MutableStateFlow(ChatState())
    val chatState = _chatState.asStateFlow()

    fun onEvent(event: ChatUIEvent) {
        when (event) {
            is ChatUIEvent.UpdatePrompt -> updatePrompt(event.newPrompt)
            is ChatUIEvent.SendPrompt -> handleSendPrompt(event.prompt, event.bitmap)
        }
    }

    private fun updatePrompt(newPrompt: String) {
        _chatState.update { it.copy(prompt = newPrompt) }
    }

    private fun handleSendPrompt(prompt: String, bitmap: Bitmap?) {
        if (prompt.isNotEmpty()) {
            addPrompt(prompt, bitmap)
            if (bitmap != null) {
                getResponseWithImage(prompt, bitmap)
            } else {
                getResponse(prompt)
            }
        }
    }

    private fun addPrompt(prompt: String, bitmap: Bitmap?) {
        _chatState.update {
            it.copy(
                chatList = it.chatList.toMutableList().apply {
                    add(0, Chat(prompt, bitmap, true))
                },
                prompt = "",
                bitmap = null
            )
        }
    }

    private fun getResponse(prompt: String) {
        viewModelScope.launch {
            val chat = ChatData.getResponse(prompt)
            _chatState.update {
                it.copy(
                    chatList = it.chatList.toMutableList().apply {
                        add(0, chat)
                    }
                )
            }
        }
    }

    private fun getResponseWithImage(prompt: String, bitmap: Bitmap) {
        viewModelScope.launch {
            val chat = ChatData.getResponseWithImage(prompt, bitmap)
            _chatState.update {
                it.copy(
                    chatList = it.chatList.toMutableList().apply {
                        add(0, chat)
                    }
                )
            }
        }
    }
}
