package com.ents_h108.petwell.view.main.featureAppointment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ents_h108.petwell.R
import com.ents_h108.petwell.data.model.Doctor
import com.ents_h108.petwell.databinding.FragmentAppointmentBinding
import com.ents_h108.petwell.utils.Utils.calculateDistance
import com.ents_h108.petwell.utils.Utils.getAddressFromLocation
import com.ents_h108.petwell.view.adapter.AppointmentAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class AppointmentFragment : Fragment() {

    private lateinit var binding: FragmentAppointmentBinding
    private lateinit var adapter: AppointmentAdapter
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAppointmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupLocation()
    }

    private fun setupRecyclerView() {
        adapter = AppointmentAdapter(object : AppointmentAdapter.OnItemClickListener {
            override fun onItemClick(item: Doctor) {
                // Handle item click
            }

            override fun onBtnClick(item: Doctor) {
                // Handle button click
            }
        })

        binding.rvAppointment.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAppointment.adapter = adapter
    }

    private fun setupLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    getAddressFromLocation(requireContext(), it.latitude, it.longitude) { address, _, _ ->
                        binding.extFloatingActionButton.text = address ?: "Unknown City"
                    }
                    val filteredDoctors = filterDoctorsByDistance(it.latitude, it.longitude)
                    binding.extFloatingActionButton.setOnClickListener {
                        navigateToMaps(filteredDoctors)
                    }
                    adapter.submitList(filteredDoctors)
                }
            }.addOnFailureListener {
                binding.extFloatingActionButton.text = getString(R.string.city_not_found)
            }
        } else {
            requestLocationPermission()
        }
    }

    private fun filterDoctorsByDistance(userLat: Double, userLon: Double): List<Doctor> {
        val doctors = listOf(
            Doctor("Dr. John Doe", "Animal Clinic A", "Veterinarian", " 8 Year", "Dokter kucing", "200.000", -7.44, 112.7183),
            Doctor("Dr. Jane Smith", "Animal Clinic B", "Veterinarian", "8 Year", "Dokter kucing", "200.000", -7.34, 112.7183),
            Doctor("Dr. Emily Johnson", "Animal Clinic C", "Veterinarian", "8 Year", "Dokter kucing", "200.000", -7.36, 112.71),
            Doctor("Dr. Michael Brown", "Animal Clinic D", "Veterinarian", "8 Year", "Dokter kucing", "200.000", -7.40, 112.713),
            Doctor("Dr. Sarah Davis", "Animal Clinic E", "Veterinarian", "8 Year", "Dokter kucing", "200.000", -7.5, 112.783),
            Doctor("Dr. Chris Wilson", "Animal Clinic F", "Veterinarian", "8 Year", "Dokter kucing", "200.000", -8.0, 112.7188),
            Doctor("Dr. Jessica Martinez", "Animal Clinic G", "Veterinarian", "8 Year", "Dokter kucing", "200.000", -7.0, 112.7)
        )

        return doctors.filter { doctor ->
            val distance = calculateDistance(userLat, userLon, doctor.lat, doctor.lon)
            distance <= 30
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    private fun navigateToMaps(doctors: List<Doctor>) {
        val bundle = Bundle().apply {
            putParcelableArrayList("doctors", ArrayList(doctors))
        }
        findNavController().navigate(R.id.action_appointmentFragment_to_mapsFragment, bundle)
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 100
    }
}
