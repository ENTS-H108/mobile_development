package com.ents_h108.petwell.view.main.featureScan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.FragmentInstructionScanBinding
import com.ents_h108.petwell.databinding.FragmentTabularBinding

class TabularFragment : Fragment() {
    private lateinit var binding: FragmentTabularBinding
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
        binding.btnScan.setOnClickListener {
            findNavController().navigate(TabularFragmentDirections.actionTabularFragmentToResultScanFragment())
        }
    }

}