package com.ents_h108.petwell.view.main.featureConsultation

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.ents_h108.petwell.data.model.Doctor
import com.ents_h108.petwell.data.repository.UserPreferences
import com.ents_h108.petwell.databinding.FragmentPaymentBinding
import com.ents_h108.petwell.utils.Result
import com.ents_h108.petwell.view.viewmodel.MainViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding
    private lateinit var doctor: Doctor
    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: PaymentFragmentArgs by navArgs()
        doctor = args.doctor!!
        setupUIandNavigation()
    }

    private fun setupUIandNavigation() {
        binding.apply {
            buttonPayConfirm.setOnClickListener {
                findNavController().navigate(PaymentFragmentDirections.actionPaymentFragmentToChatFragment())
            }
            textDoctorName.text = doctor.name
            textDoctorSpecialization.text = doctor.type
            textServiceCost.text = doctor.price
            imageDoctor.load(doctor.profpict)
            lifecycleScope.launch {
                val petActive = UserPreferences.getInstance(requireActivity().dataStore).getPetActive().first()
                petActive?.let { petId ->
                    viewModel.getPet(petId).observe(viewLifecycleOwner) { result ->
                        when (result) {
                            is Result.Success -> {
                                textPetName.text = result.data.name
                                binding.progressBarPayment.visibility = View.GONE

                            }
                            is Result.Error -> {
                                Log.d("PaymentFragment", "Error fetching pet: ${result.error}")
                                // Handle error scenario
                            }
                            is Result.Loading -> {
                                binding.progressBarPayment.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        }
    }
}
