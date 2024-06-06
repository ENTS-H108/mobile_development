package com.ents_h108.petwell.view.onboarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.fragment.findNavController
import com.ents_h108.petwell.R
import com.ents_h108.petwell.data.repository.UserPreferences
import com.ents_h108.petwell.databinding.FragmentOnboardingBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

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
        checkLoginStatus()
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

    private fun checkLoginStatus() {
        val isLogin = runBlocking { UserPreferences.getInstance(requireActivity().dataStore).getLoginStatus().first() }
        if (isLogin == true && findNavController().currentDestination?.id == R.id.onboardingFragment) {
            findNavController().navigate(OnboardingFragmentDirections.actionOnboardingToHome())
        }
    }
}