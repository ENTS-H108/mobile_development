package com.ents_h108.petwell.view.main.featureAppointment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
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
import com.ents_h108.petwell.utils.Result
import com.ents_h108.petwell.utils.Utils.filterDoctorsWithinRadius
import com.ents_h108.petwell.utils.Utils.requestLocationPermission
import com.ents_h108.petwell.utils.Utils.setupLocation
import com.ents_h108.petwell.view.adapter.AppointmentAdapter
import com.ents_h108.petwell.view.viewmodel.MainViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.androidx.viewmodel.ext.android.viewModel

class AppointmentFragment : Fragment() {

    private lateinit var binding: FragmentAppointmentBinding
    private lateinit var adapter: AppointmentAdapter
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val viewModel: MainViewModel by viewModel()

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
            override fun onItemClick(item: Doctor) {}

            override fun onBtnClick(item: Doctor) {
                findNavController().navigate(AppointmentFragmentDirections.actionAppointmentFragmentToDokterProfileAppointmentFragment(item))
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
                    viewModel.getAllDoctor().observe(viewLifecycleOwner) { result ->
                        when (result) {
                            is Result.Loading -> binding.extFloatingActionButton.text = getString(R.string.location)
                            is Result.Error -> binding.extFloatingActionButton.text = getString(R.string.location)
                            is Result.Success -> {
                                setupLocation(requireContext(), fusedLocationProviderClient) { city ->
                                    binding.extFloatingActionButton.text = city
                                }
                                val filteredDoctors = filterDoctorsWithinRadius(result.data, location.latitude, location.longitude)
                                binding.extFloatingActionButton.setOnClickListener {
                                    navigateToMaps(result.data)
                                }
                                adapter.submitList(result.data)
                            }
                        }
                    }
                }
            }.addOnFailureListener {
                binding.extFloatingActionButton.text = getString(R.string.city_not_found)
            }
        } else {
            requestLocationPermission(requireActivity())
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
