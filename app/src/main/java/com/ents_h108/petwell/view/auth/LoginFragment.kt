package com.ents_h108.petwell.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.FragmentLoginBinding
import com.ents_h108.petwell.utils.Utils.showError
import com.ents_h108.petwell.utils.Utils.showToast
import com.ents_h108.petwell.utils.Utils.resetError

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            logBtn.setOnClickListener {
                val fields = mapOf(
                    etEmail to etEmail,
                    etPassword to etPassword
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
                    findNavController().navigate(LoginFragmentDirections.actionLoginToHome())
                } else {
                    showToast(requireContext(), getString(R.string.field_empty))
                }
            }

            forgotPw.setOnClickListener {
                val email = etEmail.text.toString().trim()
                if (email.isEmpty()) {
                    showError(etEmail, requireContext())
                    showToast(requireContext(), getString(R.string.field_empty))
                } else {
                    resetError(etEmail, requireContext())
                    findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
                }
            }
            regBtn.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginToRegister())
            }

            backBtn.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}
