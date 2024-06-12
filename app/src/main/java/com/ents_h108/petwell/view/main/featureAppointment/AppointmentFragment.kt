package com.ents_h108.petwell.view.main.featureAppointment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ents_h108.petwell.R
import com.ents_h108.petwell.data.model.Doctor
import com.ents_h108.petwell.databinding.FragmentAppointmentBinding
import com.ents_h108.petwell.utils.Utils.calculateDistance
import com.ents_h108.petwell.utils.Utils.requestLocationPermission
import com.ents_h108.petwell.utils.Utils.setupLocation
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
        setupScrollListener()
    }

    private fun setupRecyclerView() {
        adapter = AppointmentAdapter(object : AppointmentAdapter.OnItemClickListener {
            override fun onItemClick(item: Doctor) {
                // Handle item click
            }

            override fun onBtnClick(item: Doctor) {
                findNavController().navigate(AppointmentFragmentDirections.actionAppointmentFragmentToDokterProfileAppointmentFragment())
            }
        })

        binding.rvAppointment.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAppointment.adapter = adapter
    }

    private fun setupLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        setupLocation(requireContext(), fusedLocationProviderClient) { city ->
            binding.extFloatingActionButton.text = city
        }

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
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
            requestLocationPermission(requireActivity())
        }
    }

    private fun filterDoctorsByDistance(userLat: Double, userLon: Double): List<Doctor> {
        val doctors = listOf(
            Doctor("7", R.drawable.doctor_7, "Dr. Dwi Putra", "RS Hewan Makmur", "Dokter Hewan", "2 Tahun", "Rp 30.000", -7.0, 112.7),
            Doctor("2", R.drawable.doctor_2, "Dr. Ahmad Santoso", "RS Hewan Senang Hati", "Dokter Hewan", "32 Tahun", "Rp 220.000", -7.34, 112.7183),
            Doctor("4", R.drawable.doctor_4, "Dr. Rina Hartati", "RS Hewan Ceria", "Dokter Hewan", "7 Tahun", "Rp 280.000", -7.40, 112.713),
            Doctor("5", R.drawable.doctor_5, "Dr. Eko Saputra", "RS Hewan Bahagia", "Dokter Hewan", "11 Tahun", "Rp 190.000", -7.5, 112.783),
            Doctor("6", R.drawable.doctor_6, "Dr. Andi Wijaya", "RS Hewan Sejahtera", "Dokter Hewan", "13 Tahun", "Rp 200.000", -8.0, 112.7188),
            Doctor("3", R.drawable.doctor_3, "Dr. Budi Prasetyo", "RS Hewan Cinta Kasih", "Dokter Hewan", "8 Tahun", "Rp 260.000", -7.36, 112.71),
            Doctor("1", R.drawable.doctor_1, "Dr. Siti Rahmawati", "RS Hewan Harapan Baru", "Dokter Hewan", "4 Tahun", "Rp 250.000", -7.44, 112.7183)
        )

        return doctors.filter { doctor ->
            val distance = calculateDistance(userLat, userLon, doctor.lat, doctor.lon)
            distance <= 30
        }
    }

    private fun navigateToMaps(doctors: List<Doctor>) {
        val bundle = Bundle().apply {
            putParcelableArrayList("doctors", ArrayList(doctors))
        }
        findNavController().navigate(R.id.action_appointmentFragment_to_mapsFragment, bundle)
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
