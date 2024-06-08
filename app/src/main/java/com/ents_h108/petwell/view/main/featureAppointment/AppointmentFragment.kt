package com.ents_h108.petwell.view.main.featureAppointment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ents_h108.petwell.databinding.FragmentAppointmentBinding
import com.ents_h108.petwell.view.adapter.AppointmentAdapter
import com.ents_h108.petwell.view.adapter.AppointmentItem

class AppointmentFragment : Fragment() {

    private lateinit var binding: FragmentAppointmentBinding
    private lateinit var adapter: AppointmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAppointmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = AppointmentAdapter(object : AppointmentAdapter.OnItemClickListener {
            override fun onItemClick(item: AppointmentItem) {
                // Handle item click
            }

            override fun onBtnClick(item: AppointmentItem) {
                findNavController().navigate(AppointmentFragmentDirections.actionAppointmentFragmentToDetailAppointment())
            }
        })

        binding.rvAppointment.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAppointment.adapter = adapter

        // Submit dummy data to the adapter
        val dummyData = listOf(
            AppointmentItem("Dr. John Doe", "Animal Clinic A", "Veterinarian", "New York", "$50"),
            AppointmentItem("Dr. Jane Smith", "Animal Clinic B", "Veterinarian", "Los Angeles", "$60"),
            AppointmentItem("Dr. Emily Johnson", "Animal Clinic C", "Veterinarian", "Chicago", "$70"),
            AppointmentItem("Dr. Michael Brown", "Animal Clinic D", "Veterinarian", "Houston", "$80"),
            AppointmentItem("Dr. Sarah Davis", "Animal Clinic E", "Veterinarian", "Phoenix", "$90"),
            AppointmentItem("Dr. Chris Wilson", "Animal Clinic F", "Veterinarian", "Philadelphia", "$100"),
            AppointmentItem("Dr. Jessica Martinez", "Animal Clinic G", "Veterinarian", "San Antonio", "$110")
        )

        adapter.submitList(dummyData)
    }
}
