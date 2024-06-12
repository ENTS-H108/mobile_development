package com.ents_h108.petwell.view.main.featureAppointment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.FragmentInvoiceAppointmentBinding


class InvoiceAppointmentFragment : Fragment() {
    private lateinit var binding: FragmentInvoiceAppointmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInvoiceAppointmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateToStatus()
    }

    private fun navigateToStatus() {
        binding.btnMakeAppointment.setOnClickListener {
            findNavController().navigate(InvoiceAppointmentFragmentDirections.actionInvoiceAppointmentFragmentToStatusFragment())
        }
    }


}