package com.ents_h108.petwell.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.FragmentLoginBinding
import com.ents_h108.petwell.utils.Result
import com.ents_h108.petwell.utils.Utils.resetError
import com.ents_h108.petwell.utils.Utils.showError
import com.ents_h108.petwell.utils.Utils.showToast
import com.ents_h108.petwell.view.viewmodel.AuthViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            logBtnLogin.setOnClickListener { validateAndLogin() }
            regBtn.setOnClickListener {findNavController().navigate(LoginFragmentDirections.actionLoginToRegister())}
            backBtn.setOnClickListener {findNavController().navigate(LoginFragmentDirections.actionLoginToOnboarding())}
            forgotPw.setOnClickListener { handleForgotPassword() }
        }
    }

    private fun validateAndLogin() {
        val email = binding.etEmailLogin.text.toString().trim()
        val password = binding.etPasswordLogin.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            showToast(requireContext(), getString(R.string.field_empty))
            return
        }

        if (!binding.etEmailLogin.isEmailValid) {
            showToast(requireContext(), getString(R.string.incorrect_email_format))
            return
        }

        if (!binding.etPasswordLogin.isPasswordValid) {
            showToast(requireContext(), getString(R.string.incorrect_pw_format))
            return
        }

        handleLogin(email, password)
    }

    private fun handleLogin(email: String, password: String) {
        authViewModel.login(email, password).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> binding.loadingLogin.visibility = View.VISIBLE
                is Result.Success -> {
                    binding.loadingLogin.visibility = View.GONE
                    showToast(requireContext(), result.data.message ?: "Login berhasil")
                    result.data.token?.let { authViewModel.saveLoginStatus(it) }
                    lifecycleScope.launch {
                        delay(2000)
                        findNavController().navigate(LoginFragmentDirections.actionLoginToHome())
                    }
                }
                is Result.Error -> {
                    binding.loadingLogin.visibility = View.GONE
                    showToast(requireContext(), result.error)
                }
            }
        }
    }

    private fun handleForgotPassword() {
        val email = binding.etEmailLogin.text.toString().trim()

        if (!binding.etEmailLogin.isEmailValid) {
            showToast(requireContext(), getString(R.string.incorrect_email_format))
            return
        }

        if (email.isEmpty()) {
            showError(binding.etEmailLogin, requireContext())
            showToast(requireContext(), getString(R.string.field_empty))
            return
        }

        resetError(binding.etEmailLogin, requireContext())
        authViewModel.requestToken(email).observe(viewLifecycleOwner) { result ->
            binding.loadingLogin.visibility = View.GONE
            when (result) {
                is Result.Success -> showToast(requireContext(), result.data.message)
                is Result.Error -> showToast(requireContext(), getString(R.string.email_not_found))
                is Result.Loading -> binding.loadingLogin.visibility = View.VISIBLE
            }
        }
    }
}
