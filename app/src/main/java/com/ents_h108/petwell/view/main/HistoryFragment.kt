package com.ents_h108.petwell.view.main

import HistoryAdapter
import HistoryItem
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ents_h108.petwell.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private val historyAdapter = HistoryAdapter(object : HistoryAdapter.OnItemClickListener {
        override fun onItemClick(item: HistoryItem) {
            // Handle item click
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = historyAdapter
        }

        val historyList = listOf(
            HistoryItem(1, "url_gambar_1", "Dr. Repabdonad", "veterinarians", "2024-06-01"),
            HistoryItem(2, "url_gambar_2", "Vaccine Calicivirus", "rumah sakit hewan jaya raya", "2024-06-02"),
            HistoryItem(3, "url_gambar_3", "Folikulitis", "Folikulitis bakteri superfisial adalah infeksi yang menyebabkan luka.....", "2024-06-03"),
            HistoryItem(1, "url_gambar_1", "Dr. Repabdonad", "veterinarians", "2024-06-01"),
            HistoryItem(2, "url_gambar_2", "Vaccine Calicivirus", "rumah sakit hewan jaya raya", "2024-06-02"),
            HistoryItem(3, "url_gambar_3", "Folikulitis", "Folikulitis bakteri superfisial adalah infeksi yang menyebabkan luka.....", "2024-06-03")
        )

        historyAdapter.submitList(historyList)
    }
}
