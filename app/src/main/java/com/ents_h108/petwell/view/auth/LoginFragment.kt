package com.ents_h108.petwell.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.FragmentLoginBinding
import com.ents_h108.petwell.view.viewmodel.AuthViewModel
import com.ents_h108.petwell.utils.Result
import com.ents_h108.petwell.utils.Utils.showError
import com.ents_h108.petwell.utils.Utils.showToast
import com.ents_h108.petwell.utils.Utils.resetError
import com.ents_h108.petwell.utils.ViewModelFactory

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val authViewModel: AuthViewModel by viewModels { ViewModelFactory() }

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
        observeViewModel()
    }

    private fun setupUI() {
        binding.apply {
            logBtn.setOnClickListener { validateAndLogin() }
            regBtn.setOnClickListener { findNavController().navigate(LoginFragmentDirections.actionLoginToRegister()) }
            backBtn.setOnClickListener { findNavController().popBackStack() }
            forgotPw.setOnClickListener {
                val email = etEmail.text.toString().trim()
                if (email.isEmpty()) {
                    showError(etEmail, requireContext())
                    showToast(requireContext(), getString(R.string.field_empty))
                } else {
                    resetError(etEmail, requireContext())
                    authViewModel.requestToken(email)
                }
            }
        }
    }

    private fun validateAndLogin() {
        val fields = mapOf(
            binding.etEmail to binding.etEmail.text.toString().trim(),
            binding.etPassword to binding.etPassword.text.toString().trim()
        )

        var allFieldsFilled = true
        for ((editText, value) in fields) {
            if (value.isEmpty()) {
                showError(editText, requireContext())
                allFieldsFilled = false
            } else {
                resetError(editText, requireContext())
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
        authViewModel.login(email, password)
    }

    private fun observeViewModel() {
        authViewModel.loginResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    showToast(requireContext(), result.data.message)
                    binding.loading.visibility = View.GONE
                    findNavController().navigate(LoginFragmentDirections.actionLoginToHome())
                }
                is Result.Error -> {
                    showToast(requireContext(), result.error)
                    binding.loading.visibility = View.GONE
                }
            }
        }
        authViewModel.resetPasswordResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    showToast(requireContext(), result.data.message)
                    binding.loading.visibility = View.GONE
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment())
                }
                is Result.Error -> {
                    binding.loading.visibility = View.GONE
                    showToast(requireContext(), getString(R.string.email_not_found))
                }
            }
        }
    }
}
