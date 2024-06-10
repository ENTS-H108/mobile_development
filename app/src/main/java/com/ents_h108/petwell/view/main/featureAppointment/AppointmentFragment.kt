package com.ents_h108.petwell.view.main.featureAppointment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ents_h108.petwell.R
import com.ents_h108.petwell.data.model.Doctor
import com.ents_h108.petwell.databinding.FragmentAppointmentBinding
import com.ents_h108.petwell.view.adapter.AppointmentAdapter

class AppointmentFragment : Fragment() {

    private lateinit var binding: FragmentAppointmentBinding
    private lateinit var adapter: AppointmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAppointmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = AppointmentAdapter(object : AppointmentAdapter.OnItemClickListener {
            override fun onItemClick(item: Doctor) {
                // Handle item click
            }

            override fun onBtnClick(item: Doctor) {
                findNavController().navigate(AppointmentFragmentDirections.actionAppointmentFragmentToDetailAppointment())
            }
        })

        binding.rvAppointment.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAppointment.adapter = adapter

        val dummyData = listOf(
            Doctor("Dr. John Doe", "Animal Clinic A", "Veterinarian", " 8 Year", "Dokter kucing", "200.000", 10.0, 11.0),
            Doctor("Dr. Jane Smith", "Animal Clinic B", "Veterinarian", "8 Year", "Dokter kucing", "200.000", 10.0, 12.0),
            Doctor("Dr. Emily Johnson", "Animal Clinic C", "Veterinarian", "8 Year", "Dokter kucing", "200.000", 10.0, 13.0),
            Doctor("Dr. Michael Brown", "Animal Clinic D", "Veterinarian", "8 Year", "Dokter kucing", "200.000", 11.0, 11.0),
            Doctor("Dr. Sarah Davis", "Animal Clinic E", "Veterinarian", "8 Year", "Dokter kucing", "200.000", 12.0, 11.0),
            Doctor("Dr. Chris Wilson", "Animal Clinic F", "Veterinarian", "8 Year", "Dokter kucing", "200.000", 13.0, 11.0),
            Doctor("Dr. Jessica Martinez", "Animal Clinic G", "Veterinarian", "8 Year", "Dokter kucing", "200.000", 10.0, 11.0)
        )

        adapter.submitList(dummyData)

        setupScrollListener()

        navigation(dummyData)

        binding.extFloatingActionButton.text = "Sidoarjo"

    }

    private fun navigation(data: List<Doctor>) {
        binding.extFloatingActionButton.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelableArrayList("doctors", ArrayList(data))
            }
            findNavController().navigate(R.id.action_appointmentFragment_to_mapsFragment, bundle)
        }
    }

    private fun setupScrollListener() {
        val fab = binding.extFloatingActionButton
        val nestedScrollView = binding.nestedScrollView

        nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY + 12 && fab.isExtended) {
                fab.shrink()
            } else if (scrollY < oldScrollY - 12 && !fab.isExtended) {
                fab.extend()
            }

            if (scrollY == 0) {
                fab.extend()
            }
        })
    }
}
