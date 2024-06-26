package com.ents_h108.petwell.view.main.featureScan

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.FragmentTabularCatBinding

class TabularCatFragment : Fragment() {

    private lateinit var binding: FragmentTabularCatBinding
    private val responsesTabular = IntArray(10) { -1 }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTabularCatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRadioGroups()
        setupButton()
    }

    private fun setupRadioGroups() {
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

        radioGroups.forEachIndexed { index, radioGroup ->
            radioGroup.setOnCheckedChangeListener { _, checkedId ->
                responsesTabular[index] = when (checkedId) {
                    radioGroup.getChildAt(0).id -> 0
                    radioGroup.getChildAt(1).id -> 1
                    radioGroup.getChildAt(2).id -> 2
                    radioGroup.getChildAt(3).id -> 3
                    else -> -1
                }
            }
        }
    }

    private fun setupButton() {
        val petType = arguments?.getString("petType") ?: return
        val uri = arguments?.getString("uri") ?: return
        binding.btnScanCat.setOnClickListener {
            if (responsesTabular.contains(-1)) {
                Toast.makeText(requireContext(), R.string.require_question, Toast.LENGTH_SHORT).show()
            } else {
                findNavController().navigate(
                    TabularCatFragmentDirections.actionTabularCatFragmentToResultScanFragment2(uri, petType, responsesTabular)
                )
            }
        }
    }
}
