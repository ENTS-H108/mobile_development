package com.ents_h108.petwell.view.main.featureAppointment

import android.animation.LayoutTransition
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ents_h108.petwell.databinding.FragmentDokterProfileAppointmentBinding

class DokterProfileAppointmentFragment : Fragment() {

private lateinit var binding: FragmentDokterProfileAppointmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDokterProfileAppointmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cvDoctorProfile.setOnClickListener { view ->
            accordionToggle(
                binding.tvDoctorProfileDescription,
                binding.llDoctorProfile
            )
        }
        binding.cvMedicalTreatment.setOnClickListener { view ->
            accordionToggle(
                binding.tvMedicalTreatmentDescription,
                binding.llMedicalTreatment
            )
        }
        binding.cvPracticalExperience.setOnClickListener { view ->
            accordionToggle(
                binding.tvPracticalExperienceDescription,
                binding.llPracticalExperience
            )
        }
        binding.cvEducationalBackground.setOnClickListener { view ->
            accordionToggle(
                binding.tvEducationalBackgroundDescription,
                binding.llEducationalBackground
            )
        }
        navigateToInvoice()
    }


    private fun accordionToggle(accordionItem: View, layout: ViewGroup) {
        layout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        TransitionManager.beginDelayedTransition(layout, AutoTransition())

        if (accordionItem.visibility == View.VISIBLE) {
            accordionItem.visibility = View.GONE
        } else {
            accordionItem.visibility = View.VISIBLE
        }
    }

    private fun navigateToInvoice(){
        binding.btnMakeAppointment.setOnClickListener {
            findNavController().navigate(DokterProfileAppointmentFragmentDirections.actionDokterProfileAppointmentFragmentToInvoiceAppointmentFragment())
        }
    }
}