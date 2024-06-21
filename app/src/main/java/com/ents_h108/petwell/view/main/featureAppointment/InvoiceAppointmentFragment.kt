package com.ents_h108.petwell.view.main.featureAppointment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ents_h108.petwell.data.model.WorkHours
import com.ents_h108.petwell.data.repository.UserPreferences
import com.ents_h108.petwell.databinding.FragmentInvoiceAppointmentBinding
import com.ents_h108.petwell.utils.Result
import com.ents_h108.petwell.view.viewmodel.MainViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

class InvoiceAppointmentFragment : Fragment() {

    private lateinit var binding: FragmentInvoiceAppointmentBinding
    private val viewModel: MainViewModel by viewModel()
    private lateinit var hour: WorkHours

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInvoiceAppointmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        binding.btnMakeAppointment.setOnClickListener {
            makeAppointment()
        }
    }

    private fun setupUI() {
        arguments?.let {
            hour = InvoiceAppointmentFragmentArgs.fromBundle(it).hour
        }

        viewModel.getInvoiceAppointment(hour.workHourId).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    binding.apply {
                        tvDoctorName.text = result.data.doctor.name
                        tvCategory.text = result.data.doctor.type
                        location.text = result.data.doctor.hospital
                        tvApppointmentDate.text = result.data.date
                        tvAppointmentTime.text = hour.availSlot
                        tvConsultationFee.text = result.data.doctor.price
                    }
                }
                is Result.Error -> {}
                is Result.Loading -> {}
            }
        }

        viewModel.fetchUserProfile().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    binding.apply {
                        tvOrdererName.text = result.data.username
                        tvOrdererEmail.text = result.data.email
                    }
                }
                is Result.Error -> {}
                is Result.Loading -> {}
            }
        }
    }

    private fun makeAppointment() {
        lifecycleScope.launch {
            val petActive = UserPreferences.getInstance(requireActivity().dataStore).getPetActive().first()
            if (petActive != null) {
                viewModel.addHistory(petActive, 2).observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Result.Success -> {
                            findNavController().navigate(
                                InvoiceAppointmentFragmentDirections.actionInvoiceAppointmentFragmentToStatusFragment()
                            )
                        }
                        is Result.Error -> {}
                        is Result.Loading -> {}
                    }
                }
            }
        }
    }
}
