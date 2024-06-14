package com.ents_h108.petwell.view.main.featureScan

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.FragmentResultScanBinding

class ResultScanFragment : Fragment() {
    private lateinit var binding: FragmentResultScanBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigation()
        val uriString = arguments?.getString("uri") ?: return
        Log.d("ResultScanFragment", "URI: $uriString")
        val uri = Uri.parse(uriString)
        binding.imagePreview.setImageURI(uri)
    }

    private fun navigation() {
        binding.btnBackToHome.setOnClickListener {
            findNavController().navigate(ResultScanFragmentDirections.actionResultScanFragmentToHomeFragment())
        }
        binding.btnAskDoctor.setOnClickListener {
            findNavController().navigate(ResultScanFragmentDirections.actionResultScanFragmentToConsultationFragment())
        }
    }
}
