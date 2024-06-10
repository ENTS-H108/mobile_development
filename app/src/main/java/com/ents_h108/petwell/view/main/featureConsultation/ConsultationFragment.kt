package com.ents_h108.petwell.view.main.featureConsultation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
            Doctor("Dr. John Doe", "Animal Clinic A", "Veterinarian", " 8 Year", "Dokter kucing", "200.000", 10.0, 11.0),
            Doctor("Dr. Jane Smith", "Animal Clinic B", "Veterinarian", "8 Year", "Dokter kucing", "200.000", 10.0, 11.0),
            Doctor("Dr. Emily Johnson", "Animal Clinic C", "Veterinarian", "8 Year", "Dokter kucing", "200.000", 10.0, 11.0),
            Doctor("Dr. Michael Brown", "Animal Clinic D", "Veterinarian", "8 Year", "Dokter kucing", "200.000", 10.0, 11.0),
            Doctor("Dr. Sarah Davis", "Animal Clinic E", "Veterinarian", "8 Year", "Dokter kucing", "200.000", 10.0, 11.0),
            Doctor("Dr. Chris Wilson", "Animal Clinic F", "Veterinarian", "8 Year", "Dokter kucing", "200.000", 10.0, 11.0),
            Doctor("Dr. Jessica Martinez", "Animal Clinic G", "Veterinarian", "8 Year", "Dokter kucing", "200.000", 10.0, 11.0)
        )
        consultationAdapter.submitList(dummyData)
    }
}
