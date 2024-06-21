package com.ents_h108.petwell.view.main.featureAppointment

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ents_h108.petwell.R
import com.ents_h108.petwell.data.model.Doctor
import com.ents_h108.petwell.databinding.FragmentDokterProfileAppointmentBinding
import com.ents_h108.petwell.utils.Result
import com.ents_h108.petwell.utils.Utils.getAddressFromLocation
import com.ents_h108.petwell.view.adapter.AppointmentDateAdapter
import com.ents_h108.petwell.view.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DokterProfileAppointmentFragment : Fragment() {

    private lateinit var binding: FragmentDokterProfileAppointmentBinding
    private lateinit var doctor: Doctor
    private lateinit var adapter: AppointmentDateAdapter
    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDokterProfileAppointmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            doctor = DokterProfileAppointmentFragmentArgs.fromBundle(it).doctor
        }
        setupRV()
        setupUI()
    }

    private fun setupRV() {
        adapter = AppointmentDateAdapter()
        binding.rvConsultationDate.layoutManager = LinearLayoutManager(requireContext())
        binding.rvConsultationDate.adapter = adapter
    }

    private fun setupUI() {
        binding.apply {
            getAddressFromLocation(requireContext(), doctor.lat, doctor.long) { _, street,  number ->
                binding.officeLocation.text = requireContext().getString(R.string.location_format, street ?: "", number ?: "")
            }
            cvDoctorProfile.setOnClickListener {
                accordionToggle(binding.tvDoctorProfileDescription, binding.llDoctorProfile)
            }
            cvEducationalBackground.setOnClickListener {
                accordionToggle(binding.tvEducationalBackgroundDescription, binding.llEducationalBackground)
            }
            navigateToInvoice()

            viewModel.getScheduleDoctor(doctor.id, null,null).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Loading -> {}
                    is Result.Success -> {
                        adapter.submitList(result.data.schedules)
                        tvDoctorName.text = result.data.doctor.name
                        tvDoctorProfileDescription.text = result.data.doctor.profile
                        roles.text = result.data.doctor.type
                        officeLocation.text = result.data.doctor.hospital
                        tvEducationalBackgroundDescription.text = result.data.doctor.experiences
                        tvPrice.text = result.data.doctor.price
                    }
                    is Result.Error -> {}
                }
            }
        }
    }

    private fun accordionToggle(accordionItem: View, layout: ViewGroup) {
        TransitionManager.beginDelayedTransition(layout, AutoTransition())

        if (accordionItem.visibility == View.VISIBLE) {
            accordionItem.visibility = View.GONE
        } else {
            accordionItem.visibility = View.VISIBLE
        }
    }

    private fun navigateToInvoice() {
        binding.btnMakeAppointment.setOnClickListener {
            findNavController().navigate(DokterProfileAppointmentFragmentDirections.actionDokterProfileAppointmentFragmentToInvoiceAppointmentFragment())
        }
    }
}
