package com.ents_h108.petwell.view.main.featureConsultation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.FragmentConsultationBinding
import com.ents_h108.petwell.view.adapter.ConsultationAdapter
import com.ents_h108.petwell.data.model.Doctor

class ConsultationFragment : Fragment() {

    private lateinit var binding: FragmentConsultationBinding
    private lateinit var consultationAdapter: ConsultationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConsultationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        populateDummyData()
    }

    private fun setupRecyclerView() {
        consultationAdapter = ConsultationAdapter(object : ConsultationAdapter.OnItemClickListener {
            override fun onItemClick(item: Doctor) {
                // Handle item click
            }

            override fun onBtnClick(item: Doctor) {
                findNavController().navigate(ConsultationFragmentDirections.actionConsultationFragmentToPaymentFragment())
            }
        })

        binding.rvListPersonChat.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = consultationAdapter
        }
    }

    private fun populateDummyData() {
        val dummyData = listOf(
            Doctor(
                "1", // ID
                R.drawable.doctor_1, // Image resource
                "Dr. Siti Rahmawati", // Female doctor
                "RS Hewan Harapan Baru", // Updated hospital name
                "Dokter Hewan",
                "4 Tahun",
                "Rp 50.000",
                -7.44,
                112.7183 // Updated latitude and longitude
            ),
            Doctor(
                "2",
                R.drawable.doctor_2,
                "Dr. Ahmad Santoso", // Male doctor
                "RS Hewan Senang Hati", // Updated hospital name
                "Dokter Hewan",
                "32 Tahun",
                "Rp 70.000",
                -7.34,
                112.7183 // Updated latitude and longitude
            ),
            Doctor(
                "3",
                R.drawable.doctor_3,
                "Dr. Budi Prasetyo", // Male doctor
                "RS Hewan Cinta Kasih", // Updated hospital name
                "Dokter Hewan",
                "8 Tahun",
                "Rp 60.000",
                -7.36,
                112.71 // Updated latitude and longitude
            ),
            Doctor(
                "4",
                R.drawable.doctor_4,
                "Dr. Rina Hartati", // Female doctor
                "RS Hewan Ceria", // Updated hospital name
                "Dokter Hewan",
                "7 Tahun",
                "Rp 80.000",
                -7.40,
                112.713 // Updated latitude and longitude
            ),
            Doctor(
                "5",
                R.drawable.doctor_5,
                "Dr. Eko Saputra", // Male doctor
                "RS Hewan Bahagia", // Updated hospital name
                "Dokter Hewan",
                "11 Tahun",
                "Rp 90.000",
                -7.5,
                112.783 // Updated latitude and longitude
            ),
            Doctor(
                "6",
                R.drawable.doctor_6,
                "Dr. Andi Wijaya",
                "RS Hewan Sejahtera", // Updated hospital name
                "Dokter Hewan",
                "13 Tahun",
                "Rp 100.000",
                -8.0,
                112.7188 // Updated latitude and longitude
            ),
            Doctor(
                "7",
                R.drawable.doctor_7,
                "Dr. Dwi Putra",
                "RS Hewan Makmur", // Updated hospital name
                "Dokter Hewan",
                "2 Tahun",
                "Rp 30.000",
                -7.0,
                112.7 // Updated latitude and longitude
            )
        )

        consultationAdapter.submitList(dummyData)
    }
}
