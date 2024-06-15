package com.ents_h108.petwell.view.onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ents_h108.petwell.BuildConfig
import com.ents_h108.petwell.R
import com.ents_h108.petwell.data.repository.UserPreferences
import com.ents_h108.petwell.databinding.FragmentOnboardingBinding
import com.ents_h108.petwell.utils.Result
import com.ents_h108.petwell.utils.Utils
import com.ents_h108.petwell.utils.Utils.showToast
import com.ents_h108.petwell.view.viewmodel.AuthViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

class OnboardingFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingBinding
    private val credentialManager: CredentialManager by inject()
    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        checkLoginStatus()
    }

    private fun setupUI() {
        binding.apply {
            skipBtn.setOnClickListener {
                onboardingFragment.transitionToState(R.id.tabs_ob2)
                onboardingFragment.transitionToState(R.id.tabs_ob3)
            }
            logBtn.setOnClickListener {
                findNavController().navigate(OnboardingFragmentDirections.actionOnboardingToLogin())
            }
            regBtn.setOnClickListener {
                findNavController().navigate(OnboardingFragmentDirections.actionOnboardingToRegister())
            }
            googleBtn.setOnClickListener {
                handleGoogleAuth()
            }
        }
    }

    private fun checkLoginStatus() {
        lifecycleScope.launch {
            val isLogin = UserPreferences.getInstance(requireActivity().dataStore).getLoginStatus().first()
            if (isLogin == true && findNavController().currentDestination?.id == R.id.onboardingFragment) {
                findNavController().navigate(OnboardingFragmentDirections.actionOnboardingToHome())
            }
        }
    }

    private fun handleGoogleAuth() {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(BuildConfig.GOOGLE_WEB_CLIENT_ID)
            .setNonce(Utils.generateHashedNonce())
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        lifecycleScope.launch {
            try {
                val result = credentialManager.getCredential(request = request, context = requireContext())
                val credential = result.credential
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                handleGoogleAuth(googleIdTokenCredential.idToken)
            } catch (e: GetCredentialException) {
                handleError("Failed to retrieve credentials. Please try again.", e)
            } catch (e: GoogleIdTokenParsingException) {
                handleError("Failed to parse Google ID Token. Please try again.", e)
            } catch (e: Exception) {
                handleError("An unexpected error occurred. Please try again.", e)
            }
        }
    }

    private fun handleGoogleAuth(idToken: String) {
        authViewModel.googleAuth(idToken).observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    showLoading(false)
                    it.data.token?.let { token -> authViewModel.saveLoginStatus(token) }
                    navigateToHome()
                }
                is Result.Error -> {
                    showLoading(false)
                    showToast(requireContext(), it.error)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun navigateToHome() {
        lifecycleScope.launch {
            delay(1000)
            findNavController().navigate(OnboardingFragmentDirections.actionOnboardingToHome())
        }
    }

    private fun handleError(message: String, exception: Exception) {
        showToast(requireContext(), message)
        exception.printStackTrace() // Log the exception for debugging
    }
}
