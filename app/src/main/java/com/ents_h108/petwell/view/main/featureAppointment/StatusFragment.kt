package com.ents_h108.petwell.view.main.featureAppointment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.FragmentInvoiceAppointmentBinding
import com.ents_h108.petwell.databinding.FragmentStatusBinding


class StatusFragment : Fragment() {
    private lateinit var binding: FragmentStatusBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatusBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigate()
    }

    private fun navigate() {
        binding.btnToHome.setOnClickListener {
        findNavController().navigate(StatusFragmentDirections.actionStatusFragmentToHomeFragment())
        }
        binding.btnToHistory.setOnClickListener { findNavController().navigate(StatusFragmentDirections.actionStatusFragmentToHistoryFragment()) }
    }
}

