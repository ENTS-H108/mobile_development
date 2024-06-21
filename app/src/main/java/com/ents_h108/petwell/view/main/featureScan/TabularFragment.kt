package com.ents_h108.petwell.view.main.featureScan

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.fragment.findNavController
import com.ents_h108.petwell.R
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
        val petType = arguments?.getString("petType") ?: return

        binding.btnNextTabular.setOnClickListener {

            val responsesTabular = IntArray(10)
            val radioGroups = listOf(
                binding.radioGroup1,
                binding.radioGroup2,
                binding.radioGroup3,
                binding.radioGroup4,
                binding.radioGroup5,
                binding.radioGroup6,
                binding.radioGroup7,
                binding.radioGroup8,
                binding.radioGroup9,
                binding.radioGroup10
            )

            var allAnswered = true

            for (i in radioGroups.indices) {
                val selectedId = radioGroups[i].checkedRadioButtonId
                if (selectedId == -1) {
                    allAnswered = false
                    break
                } else {
                    val radioButton = radioGroups[i].findViewById<RadioButton>(selectedId)
                    responsesTabular[i] = when (radioButton.text) {
                        getString(R.string.tabular_option_none) -> 0
                        getString(R.string.tabular_option_not_dominant) -> 1
                        getString(R.string.tabular_option_dominant) -> 2
                        getString(R.string.tabular_option_worse) -> 3
                        else -> -1
                    }
                }
            }

            if (!allAnswered) {
                Toast.makeText(requireContext(), R.string.require_question, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Log.d("TabularFragment", "Responses: ${responsesTabular.joinToString(", ")}")
            Log.d("TabularFragment", "Responses: $petType")



            if (petActive != null) {
                viewModel.addHistory(petActive, 3).observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Result.Loading -> {
                        }

                        is Result.Success -> {
                            if (petType.contains("ca") ) {
                                findNavController().navigate(
                                    TabularFragmentDirections.actionTabularFragmentToTabularCatFragment(uri,petType)
                                )
                            } else if (petType == "dog") {
                                findNavController().navigate(
                                    TabularFragmentDirections.actionTabularFragmentToTabularDogFragment(uri,petType)
                                )
                            }
                        }

                        is Result.Error -> {
                        }
                    }
                }
            }
        }
    }
}
