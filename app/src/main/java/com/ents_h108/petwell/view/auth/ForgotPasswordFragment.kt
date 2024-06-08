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

        val token = args.token
        setupUI(token)
    }

    private fun setupUI(token: String) {
        binding.apply {
            resetBtn.setOnClickListener { validateAndResetPassword(token) }
            backBtn.setOnClickListener { findNavController().navigate(ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToLoginFragment()) }
        }
    }

    private fun validateAndResetPassword(token: String) {
        val newPassword = binding.etNewPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()

        when {
            newPassword.isEmpty() -> showToast(requireContext(), getString(R.string.field_empty))
            confirmPassword.isEmpty() -> showToast(requireContext(), getString(R.string.field_empty))
            newPassword != confirmPassword -> showToast(requireContext(), getString(R.string.cpassword_not_match))
            else -> resetPassword(token, newPassword)
        }
    }

    private fun resetPassword(token: String, newPassword: String) {
        authViewModel.resetPassword(newPassword, token).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> binding.loading.visibility = View.GONE
                is Result.Success -> {
                    showToast(requireContext(), result.data.message)
                    findNavController().navigate(ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToLoginFragment())
                }
                is Result.Error -> showToast(requireContext(), getString(R.string.token_mismatch))
            }
        }
    }
}
