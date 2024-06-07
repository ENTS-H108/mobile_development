package com.ents_h108.petwell.view.onboarding

import android.content.Context
import androidx.credentials.CredentialManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialRequest.Builder
import androidx.credentials.exceptions.GetCredentialException
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.fragment.findNavController
import com.ents_h108.petwell.BuildConfig
import com.ents_h108.petwell.R
import com.ents_h108.petwell.data.repository.UserPreferences
import com.ents_h108.petwell.databinding.FragmentOnboardingBinding
import com.ents_h108.petwell.utils.Utils
import com.ents_h108.petwell.utils.Utils.showToast
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

class OnboardingFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingBinding
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val credentialManager: CredentialManager by inject()

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
        binding.googleBtn.setOnClickListener {
            googleCredentialManager()
        }
    }

    private fun checkLoginStatus() {
        coroutineScope.launch {
            val isLogin = UserPreferences.getInstance(requireActivity().dataStore).getLoginStatus().first()
            if (isLogin == true && findNavController().currentDestination?.id == R.id.onboardingFragment) {
                findNavController().navigate(OnboardingFragmentDirections.actionOnboardingToHome())
            }
        }
    }

    private fun googleCredentialManager() {
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(BuildConfig.GOOGLE_WEB_CLIENT_ID)
            .setNonce(Utils.generateHashedNonce())
            .build()

        val request: GetCredentialRequest = Builder()
            .addCredentialOption(googleIdOption)
            .build()

        coroutineScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = requireContext(),
                )
                val credential = result.credential
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val googleIdToken = googleIdTokenCredential.idToken
                Log.i("Success", "Google ID Token: $googleIdToken")
                findNavController().navigate(OnboardingFragmentDirections.actionOnboardingToHome())
            } catch (e: GetCredentialException) {
                showToast(requireContext(),"Failed to retrieve credentials. Please try again.")
            } catch (e: GoogleIdTokenParsingException) {
                showToast(requireContext(),"Failed to parse Google ID Token. Please try again.")
            } catch (e: Exception) {
                showToast(requireContext(),"An unexpected error occurred. Please try again.")
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}
