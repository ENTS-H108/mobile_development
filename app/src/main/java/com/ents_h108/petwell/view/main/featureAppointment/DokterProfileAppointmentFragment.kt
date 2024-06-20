package com.ents_h108.petwell.view.main.featureAppointment

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ents_h108.petwell.R
import com.ents_h108.petwell.data.model.Doctor
import com.ents_h108.petwell.databinding.FragmentDokterProfileAppointmentBinding
import com.ents_h108.petwell.utils.Utils.getAddressFromLocation
import com.ents_h108.petwell.view.main.featureConsultation.PaymentFragmentArgs

class DokterProfileAppointmentFragment : Fragment() {

    private lateinit var binding: FragmentDokterProfileAppointmentBinding
    private lateinit var doctor: Doctor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDokterProfileAppointmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: DokterProfileAppointmentFragmentArgs by navArgs()
        doctor = args.doctor!!

        binding.tvDoctorName.text = doctor.name
        binding.tvDoctorProfileDescription.text = doctor.profile
        binding.roles.text = doctor.type
        binding.officeLocation.text = doctor.hospital
        binding.tvEducationalBackgroundDescription.text = doctor.experiences
        getAddressFromLocation(requireContext(), doctor.lat, doctor.long) { _, street,  number ->
            binding.officeLocation.text = requireContext().getString(R.string.location_format, street ?: "", number ?: "")
        }
        binding.cvDoctorProfile.setOnClickListener {
            accordionToggle(binding.tvDoctorProfileDescription, binding.llDoctorProfile)
        }

        binding.cvEducationalBackground.setOnClickListener {
            accordionToggle(binding.tvEducationalBackgroundDescription, binding.llEducationalBackground)
        }

        navigateToInvoice()
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
