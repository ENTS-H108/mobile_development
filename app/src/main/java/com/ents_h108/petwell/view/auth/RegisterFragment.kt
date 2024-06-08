package com.ents_h108.petwell.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.navigation.fragment.findNavController
import com.ents_h108.petwell.utils.Result
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.FragmentRegisterBinding
import com.ents_h108.petwell.utils.Utils.resetError
import com.ents_h108.petwell.utils.Utils.showError
import com.ents_h108.petwell.utils.Utils.showToast
import com.ents_h108.petwell.view.viewmodel.AuthViewModel

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            regBtn.setOnClickListener { handleRegister() }
            logBtn.setOnClickListener { findNavController().navigate(RegisterFragmentDirections.actionRegisterToLogin()) }
            backBtn.setOnClickListener { findNavController().navigate(RegisterFragmentDirections.actionRegisterToOnboarding()) }
        }
    }

    private fun handleRegister() {
        with(binding) {
            val fields = mapOf(
                etUsername to etUsername.text.toString().trim(),
                etEmail to etEmail.text.toString().trim(),
                etPassword to etPassword.text.toString().trim(),
                etCpassword to etCpassword.text.toString().trim()
            )

            if (fields.values.any { it.isEmpty() }) {
                fields.forEach { (editText, value) ->
                    if (value.isEmpty()) showError(editText, requireContext()) else resetError(editText, requireContext())
                }
                showToast(requireContext(), getString(R.string.field_empty))
                return
            }

            if (etPassword.text.toString() != etCpassword.text.toString()) {
                showError(etCpassword, requireContext())
                showToast(requireContext(), getString(R.string.cpassword_not_match))
                return
            }

            authViewModel.register(fields.getValue(etEmail), fields.getValue(etUsername), fields.getValue(etPassword))
                .observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Result.Loading -> loading.visibility = View.VISIBLE
                        is Result.Success -> handleRegistrationSuccess(fields.getValue(etEmail))
                        is Result.Error -> handleRegistrationError(result.error)
                    }
                }
        }
    }

    private fun handleRegistrationSuccess(email: String) {
        with(binding) {
            loading.visibility = View.GONE
            showToast(requireContext(), getString(R.string.email_verification_sent, email))
            findNavController().navigate(RegisterFragmentDirections.actionRegisterToLogin())
        }
    }

    private fun handleRegistrationError(error: String) {
        with(binding) {
            loading.visibility = View.GONE
            showToast(requireContext(), error)
            etEmail.text?.clear()
            etPassword.text?.clear()
            etCpassword.text?.clear()
        }
    }
}
