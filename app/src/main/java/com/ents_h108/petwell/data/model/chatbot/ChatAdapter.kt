package com.ents_h108.petwell.view.main.featureConsultation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ents_h108.petwell.data.model.chatbot.Chat
import com.ents_h108.petwell.databinding.ItemMessageReceivedBinding
import com.ents_h108.petwell.databinding.ItemMessageSentBinding
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var chatList: List<Chat> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SENT) {
            val binding = ItemMessageSentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            SentMessageViewHolder(binding)
        } else {
            val binding = ItemMessageReceivedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ReceivedMessageViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chat = chatList[position]
        if (holder is SentMessageViewHolder) {
            holder.bind(chat)
        } else if (holder is ReceivedMessageViewHolder) {
            holder.bind(chat)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (chatList[position].isFromUser) {
            VIEW_TYPE_SENT
        } else {
            VIEW_TYPE_RECEIVED
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    fun submitList(newChatList: List<Chat>) {
        chatList = newChatList
        notifyDataSetChanged()
    }

    class SentMessageViewHolder(private val binding: ItemMessageSentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat) {
            binding.textMessageSent.text = chat.prompt
            binding.textTimeSent.text = getCurrentTime()
        }
    }

    class ReceivedMessageViewHolder(private val binding: ItemMessageReceivedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat) {
            binding.textMessageReceived.text = chat.prompt
            binding.textTimeReceived.text = getCurrentTime()
        }
    }

    companion object {
        private const val VIEW_TYPE_SENT = 1
        private const val VIEW_TYPE_RECEIVED = 2
        private fun getCurrentTime(): String {
            val currentTime = LocalTime.now()
            val formatter = DateTimeFormatter.ofPattern("HH.mm")
            return currentTime.format(formatter)
        }
    }
}
