package com.ents_h108.petwell.data.model.chatbot

import android.graphics.Bitmap

data class ChatState(
    val chatList: MutableList<Chat> = mutableListOf(),
    val prompt: String = "",
    val bitmap: Bitmap? = null
)
