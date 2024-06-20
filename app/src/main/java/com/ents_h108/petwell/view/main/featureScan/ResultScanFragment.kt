package com.ents_h108.petwell.view.main.featureScan

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ents_h108.petwell.databinding.FragmentResultScanBinding
import com.ents_h108.petwell.view.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResultScanFragment : Fragment() {
    private lateinit var binding: FragmentResultScanBinding
    private val viewModel: MainViewModel by viewModel()



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
        viewModel.imageUri.observe(viewLifecycleOwner) { uriString ->
            val uri = Uri.parse(uriString)
            binding.imagePreview.setImageURI(uri)
            Log.d("ResultScanFragment", "URI: $uriString")
        }

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
