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
    private lateinit var historyadapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val historyList = listOf(
            HistoryItem(
                type = 1,
                imageUrl = "url_gambar_1",
                title = "Dr. Repabdonad ",
                deskripsi = "veterinarians",
                tanggal = "2024-06-01"
            ),
            HistoryItem(
                type = 2,
                imageUrl = "url_gambar_2",
                title = "Vaccine Calicivirus",
                deskripsi = "rumah sakit hewan jaya raya",
                tanggal = "2024-06-02"
            ),
            HistoryItem(
                type = 3,
                imageUrl = "url_gambar_3",
                title = "Folikulitis",
                deskripsi = "Folikulitis bakteri superfisial adalah infeksi yang menyebabkan luka.....",
                tanggal = "2024-06-03"
            ),
            HistoryItem(
                type = 1,
                imageUrl = "url_gambar_1",
                title = "Dr. Repabdonad ",
                deskripsi = "veterinarians",
                tanggal = "2024-06-01"
            ),
            HistoryItem(
                type = 2,
                imageUrl = "url_gambar_2",
                title = "Vaccine Calicivirus",
                deskripsi = "rumah sakit hewan jaya raya",
                tanggal = "2024-06-02"
            ),
            HistoryItem(
                type = 3,
                imageUrl = "url_gambar_3",
                title = "Folikulitis",
                deskripsi = "Folikulitis bakteri superfisial adalah infeksi yang menyebabkan luka.....",
                tanggal = "2024-06-03"
            )
        )

        historyadapter = HistoryAdapter(object : HistoryAdapter.OnItemClickListener {
            override fun onItemClick(item: HistoryItem) {
                // Handle item click
            }

        })

        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = historyadapter
        }

        historyadapter.submitList(historyList)
    }
}

