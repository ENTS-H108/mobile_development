package com.ents_h108.petwell.view.main.featureScan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.FragmentChosePetBinding

class ChosePetFragment : Fragment() {
    private lateinit var binding: FragmentChosePetBinding
    private var selectedPet: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChosePetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        updateNextButtonState()
    }

    private fun setupListeners() {
        binding.cardCat.setOnClickListener {
            selectPet("cat")
        }

        binding.cardDog.setOnClickListener {
            selectPet("dog")
        }

        binding.btnNext.setOnClickListener {
            if (selectedPet != null) {
                findNavController().navigate(ChosePetFragmentDirections.actionChosePetFragmentToInstructionScanFragment())
            }
        }
    }

    private fun selectPet(pet: String) {
        selectedPet = pet

        if (pet == "cat"){
            binding.cardCat.setBackgroundResource(R.drawable.card_pet_active)
            binding.cardDog.setBackgroundResource(android.R.color.white)
        }else{
            binding.cardDog.setBackgroundResource(R.drawable.card_pet_active)
            binding.cardCat.setBackgroundResource(android.R.color.white)
        }


        updateNextButtonState()
    }

    private fun updateNextButtonState() {
        binding.btnNext.isEnabled = selectedPet != null
        binding.btnNext.setBackgroundResource(
            if (selectedPet != null) R.drawable.btn_yellow_states
            else R.drawable.btn_yellow_disable
        )
    }
}
