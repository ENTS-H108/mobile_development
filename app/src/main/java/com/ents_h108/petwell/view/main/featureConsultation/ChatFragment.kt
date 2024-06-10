package com.ents_h108.petwell.view.main.featureConsultation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ents_h108.petwell.data.model.chatbot.ChatUIEvent
import com.ents_h108.petwell.data.model.chatbot.ChatViewModel
import com.ents_h108.petwell.databinding.FragmentChatBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    private val chatViewModel: ChatViewModel by viewModel()
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        binding.sendButton.setOnClickListener {
            val prompt = binding.etInputMessage.text.toString()
            chatViewModel.onEvent(ChatUIEvent.SendPrompt(prompt, null))
            binding.etInputMessage.text.clear()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            chatViewModel.chatState.collect { state ->
                chatAdapter.submitList(state.chatList)
            }
        }
    }

    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter()
        binding.rvChat.apply {
            layoutManager = LinearLayoutManager(context).apply {
                reverseLayout = true
            }
            adapter = chatAdapter
        }
    }
}
