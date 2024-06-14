package com.ents_h108.petwell.view.main.featureScan

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
import com.ents_h108.petwell.databinding.FragmentTabularBinding
import com.ents_h108.petwell.utils.Result
import com.ents_h108.petwell.view.viewmodel.MainViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.viewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

class TabularFragment : Fragment() {

    private lateinit var binding: FragmentTabularBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTabularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigation()
    }

    private fun navigation() {
        val petActive = runBlocking {
            UserPreferences.getInstance(requireActivity().dataStore).getPetActive().first()
        }
        val uri = arguments?.getString("uri") ?: return
        binding.btnScan.setOnClickListener {
            if (petActive != null) {
                viewModel.addHistory(petActive, 3).observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Result.Loading -> {
                            // Handle loading state if needed
                        }
                        is Result.Success -> {
                            findNavController().navigate(TabularFragmentDirections.actionTabularFragmentToResultScanFragment(uri))
                        }
                        is Result.Error -> {
                            // Handle error state if needed
                        }
                    }
                }
            }
        }
    }
}
