package com.ents_h108.petwell.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ents_h108.petwell.utils.Result
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.FragmentRegisterBinding
import com.ents_h108.petwell.utils.Utils.resetError
import com.ents_h108.petwell.utils.Utils.showError
import com.ents_h108.petwell.utils.Utils.showToast
import com.ents_h108.petwell.utils.ViewModelFactory
import com.ents_h108.petwell.view.viewmodel.AuthViewModel

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val authViewModel: AuthViewModel by viewModels { ViewModelFactory() }

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
        observeViewModel()
    }

    private fun setupUI() {
        binding.apply {
            regBtn.setOnClickListener {
                val fields = mapOf(
                    etUsername to etUsername,
                    etEmail to etEmail,
                    etPassword to etPassword,
                    etCpassword to etCpassword
                )
                var allFieldsFilled = true
                fields.forEach { (editText, value) ->
                    if (value.text.toString().trim().isEmpty()) {
                        showError(editText, requireContext())
                        allFieldsFilled = false
                    } else {
                        resetError(editText, requireContext())
                    }
                }

                if (allFieldsFilled) {
                    if (etPassword.text.toString() != etCpassword.text.toString()) {
                        showError(etCpassword, requireContext())
                        showToast(requireContext(), getString(R.string.cpassword_not_match))
                    } else {
                        val email = etEmail.text.toString().trim()
                        val username = etUsername.text.toString().trim()
                        val password = etPassword.text.toString().trim()
                        authViewModel.register(email, username, password)
                    }
                } else {
                    showToast(requireContext(), getString(R.string.field_empty))
                }
            }
            logBtn.setOnClickListener {
                findNavController().navigate(RegisterFragmentDirections.actionRegisterToLogin())
            }
            backBtn.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun observeViewModel() {
        authViewModel.registerResult.observe(viewLifecycleOwner) { result ->
            binding.apply {
                when (result) {
                    is Result.Loading -> {
                        loading.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        loading.visibility = View.GONE
                        showToast(requireContext(), getString(R.string.email_verification_sent, email))
                        findNavController().navigate(RegisterFragmentDirections.actionRegisterToLogin())
                    }
                    is Result.Error -> {
                        loading.visibility = View.GONE
                        showToast(requireContext(), result.error)
                        etEmail.text?.clear()
                        etPassword.text?.clear()
                        etCpassword.text?.clear()
                    }
                }
            }
        }
    }
}
