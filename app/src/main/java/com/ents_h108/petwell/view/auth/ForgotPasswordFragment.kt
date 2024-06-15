package com.ents_h108.petwell.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.FragmentForgotPasswordBinding
import com.ents_h108.petwell.utils.Result
import com.ents_h108.petwell.utils.Utils.showToast
import com.ents_h108.petwell.view.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgotPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgotPasswordBinding
    private val authViewModel: AuthViewModel by viewModel()
    private val args: ForgotPasswordFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            val token = args.token
            resetBtn.setOnClickListener { validateAndResetPassword(token) }
            backBtn.setOnClickListener { findNavController().navigate(ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToLoginFragment()) }
        }
    }

    private fun validateAndResetPassword(token: String) {
        binding.apply {
            val newPassword = etNewPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()

            if (!etNewPassword.isPasswordValid) {
                showToast(requireContext(), getString(R.string.incorrect_pw_format))
                return
            }

            when {
                newPassword.isEmpty() -> showToast(requireContext(), getString(R.string.field_empty))
                confirmPassword.isEmpty() -> showToast(requireContext(), getString(R.string.field_empty))
                newPassword != confirmPassword -> showToast(requireContext(), getString(R.string.cpassword_not_match))
                else -> resetPassword(token, newPassword)
            }
        }
    }

    private fun resetPassword(token: String, newPassword: String) {
        authViewModel.resetPassword(newPassword, token).observe(viewLifecycleOwner) { result ->
            binding.loading.visibility = View.GONE
            when (result) {
                is Result.Success -> {
                    binding.loading.visibility = View.GONE
                    showToast(requireContext(), result.data.message)
                    findNavController().navigate(ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToLoginFragment())
                }
                is Result.Error -> {
                    binding.loading.visibility = View.GONE
                    showToast(requireContext(), getString(R.string.token_mismatch))
                }
                is Result.Loading -> binding.loading.visibility = View.VISIBLE
            }
        }
    }
}
