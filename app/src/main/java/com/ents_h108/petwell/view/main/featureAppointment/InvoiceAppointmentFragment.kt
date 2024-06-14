package com.ents_h108.petwell.view.main.featureAppointment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.fragment.findNavController
import com.ents_h108.petwell.data.repository.UserPreferences
import com.ents_h108.petwell.databinding.FragmentInvoiceAppointmentBinding
import com.ents_h108.petwell.view.viewmodel.MainViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.ents_h108.petwell.utils.Result

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

class InvoiceAppointmentFragment : Fragment() {
    private lateinit var binding: FragmentInvoiceAppointmentBinding
    private val viewModel: MainViewModel by viewModel()

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
        val petActive = runBlocking {
            UserPreferences.getInstance(requireActivity().dataStore).getPetActive().first()
        }
        binding.btnMakeAppointment.setOnClickListener {
            if (petActive != null) {
                viewModel.addHistory(petActive, 2).observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Result.Loading ->  {

                        }
                        is Result.Success -> findNavController().navigate(InvoiceAppointmentFragmentDirections.actionInvoiceAppointmentFragmentToStatusFragment())
                        is Result.Error -> {

                        }
                    }
                }
            }
        }
    }
}