package com.ents_h108.petwell.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.FragmentLoginBinding
import com.ents_h108.petwell.utils.Result
import com.ents_h108.petwell.utils.Utils.resetError
import com.ents_h108.petwell.utils.Utils.showError
import com.ents_h108.petwell.utils.Utils.showToast
import com.ents_h108.petwell.view.viewmodel.AuthViewModel
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
            logBtn.setOnClickListener { validateAndLogin() }
            regBtn.setOnClickListener { findNavController().navigate(LoginFragmentDirections.actionLoginToRegister()) }
            backBtn.setOnClickListener { findNavController().navigate(LoginFragmentDirections.actionLoginToOnboarding()) }
            forgotPw.setOnClickListener { handleForgotPassword() }
        }
    }

    private fun validateAndLogin() {
        val fields = listOf(binding.etEmail, binding.etPassword)
        val allFieldsFilled = fields.all { editText ->
            val value = editText.text.toString().trim()
            if (value.isEmpty()) {
                showError(editText, requireContext())
                false
            } else {
                resetError(editText, requireContext())
                true
            }
        }

        if (allFieldsFilled) {
            handleLogin()
        } else {
            showToast(requireContext(), getString(R.string.field_empty))
        }
    }

    private fun handleLogin() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        authViewModel.login(email, password).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> binding.loading.visibility = View.VISIBLE
                is Result.Success -> {
                    binding.loading.visibility = View.GONE
                    showToast(requireContext(), result.data.message)
                    result.data.user.let {
                        authViewModel.saveLoginStatus(it.token, it.username, it.email)
                        findNavController().navigate(LoginFragmentDirections.actionLoginToHome())
                    }
                }

                is Result.Error -> {
                    binding.loading.visibility = View.GONE
                    showToast(requireContext(), result.error)
                }
            }
        }
    }

    private fun handleForgotPassword() {
        val email = binding.etEmail.text.toString().trim()
        if (email.isEmpty()) {
            showError(binding.etEmail, requireContext())
            showToast(requireContext(), getString(R.string.field_empty))
        } else {
            resetError(binding.etEmail, requireContext())
            authViewModel.requestToken(email).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Loading -> binding.loading.visibility = View.VISIBLE
                    is Result.Success -> {
                        binding.loading.visibility = View.GONE
                        showToast(requireContext(), result.data.message)
                    }

                    is Result.Error -> {
                        binding.loading.visibility = View.GONE
                        showToast(requireContext(), getString(R.string.email_not_found))
                    }
                }
            }
        }
    }
}

