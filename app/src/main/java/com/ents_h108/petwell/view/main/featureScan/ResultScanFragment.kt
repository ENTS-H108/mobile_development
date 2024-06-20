package com.ents_h108.petwell.view.main.featureScan

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.FragmentResultScanBinding
import com.ents_h108.petwell.view.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResultScanFragment : Fragment() {
    private lateinit var binding: FragmentResultScanBinding
    private var isPenyakitLuarExpanded = false
    private var isPenyakitDalamExpanded = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeUri()
        descriptionTextExternalDisease(1)
        descriptionTextInternalDisease(2)
    }

    private fun setupViews() {
        binding.cvPenyakitLuar.setOnClickListener {
            accordionToggle(
                binding.tvPenyakitLuarDescription,
                binding.llPenyakitLuar,
                !isPenyakitLuarExpanded
            )
            isPenyakitLuarExpanded = !isPenyakitLuarExpanded
        }

        binding.cvPenyakitDalam.setOnClickListener {
            accordionToggle(
                binding.tvPenyakitDalamDescription,
                binding.llPenyakitDalam,
                !isPenyakitDalamExpanded
            )
            isPenyakitDalamExpanded = !isPenyakitDalamExpanded
        }

        binding.btnBackToHome.setOnClickListener {
            findNavController().navigate(ResultScanFragmentDirections.actionResultScanFragmentToHomeFragment())
        }

        binding.btnAskDoctor.setOnClickListener {
            findNavController().navigate(ResultScanFragmentDirections.actionResultScanFragmentToConsultationFragment())
        }
    }

    private fun accordionToggle(
        accordionItem: View,
        layout: ViewGroup,
        expand: Boolean
    ) {
        accordionItem.visibility = if (expand) View.VISIBLE else View.GONE
        layout.requestLayout()
    }

    private fun observeUri() {
        val uriString = arguments?.getString("uri") ?: return
        val uri = Uri.parse(uriString)
        binding.imagePreview.setImageURI(uri)
        Log.d("ResultScanFragment", "URI: $uriString")
    }

    private fun descriptionTextExternalDisease(result: Int) {
        val descriptionResId = when (result) {
            0 -> R.string.penyakit_luar0
            1 -> R.string.penyakit_luar1
            2 -> R.string.penyakit_luar2
            3 -> R.string.penyakit_luar3
            else -> return  // optional, handle unexpected cases
        }
        binding.tvPenyakitLuarDescription.text = getString(descriptionResId)
    }

    private fun descriptionTextInternalDisease(resultInt: Int) {
        val petType = arguments?.getString("petType") ?: return
        val descriptionResId = when (petType) {
            "cat" -> {
                when (resultInt) {
                    0 -> R.string.penyakit_dalam_kucing0
                    1 -> R.string.penyakit_dalam_kucing1
                    2 -> R.string.penyakit_dalam_kucing2
                    3 -> R.string.penyakit_dalam_kucing3
                    else -> return
                }
            }
            else -> {
                when (resultInt) {
                    0 -> R.string.penyakit_dalam_anjing0
                    1 -> R.string.penyakit_dalam_anjing1
                    2 -> R.string.penyakit_dalam_anjing2
                    3 -> R.string.penyakit_dalam_anjing3
                    else -> return
                }
            }
        }
        binding.tvPenyakitDalamDescription.text = getString(descriptionResId)
    }

}
