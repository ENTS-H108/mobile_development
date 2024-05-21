package com.ents_h108.petwell.view.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.skipBtn.setOnClickListener {
            binding.onboardingFragment.transitionToState(R.id.tabs_ob2)
            binding.onboardingFragment.transitionToState(R.id.tabs_ob3)
        }
        binding.logBtn.setOnClickListener {
            findNavController().navigate(OnboardingFragmentDirections.actionOnboardingToLogin())
        }
        binding.regBtn.setOnClickListener {
            findNavController().navigate(OnboardingFragmentDirections.actionOnboardingToRegister())
        }
    }
}