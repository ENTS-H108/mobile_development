package com.ents_h108.petwell.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.FragmentForgotPasswordBinding
import com.ents_h108.petwell.utils.ViewModelFactory
import com.ents_h108.petwell.view.viewmodel.AuthViewModel
import com.ents_h108.petwell.utils.Result
import com.ents_h108.petwell.utils.Utils.showToast

class ForgotPasswordFragment : Fragment() {
    private lateinit var binding: FragmentForgotPasswordBinding
    private val authViewModel: AuthViewModel by viewModels { ViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.resetBtn.setOnClickListener {
            validateAndResetPassword()
        }
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        observeResetPasswordResult()
    }

    private fun validateAndResetPassword() {
        val token = binding.etToken.text.toString().trim()
        val newPassword = binding.etNewPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()

        when {
            newPassword.isEmpty() -> {
                Toast.makeText(requireContext(), getString(R.string.new_password_empty), Toast.LENGTH_SHORT).show()
            }
            confirmPassword.isEmpty() -> {
                Toast.makeText(requireContext(), getString(R.string.confirm_password_empty), Toast.LENGTH_SHORT).show()
            }
            newPassword != confirmPassword -> {
                Toast.makeText(requireContext(), getString(R.string.passwords_do_not_match), Toast.LENGTH_SHORT).show()
            }
            else -> {
                authViewModel.resetPassword(token, newPassword)
            }
        }
    }

    private fun observeResetPasswordResult() {
        authViewModel.resetPasswordResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.loading.visibility = View.GONE
                    Toast.makeText(requireContext(), result.data.message, Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
                is Result.Error -> {
                    binding.loading.visibility = View.GONE
                    showToast(requireContext(), getString(R.string.token_mismatch))
                }
            }
        }
    }
}
