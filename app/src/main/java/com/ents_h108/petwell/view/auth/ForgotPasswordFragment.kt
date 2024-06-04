package com.ents_h108.petwell.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.FragmentForgotPasswordBinding
import com.ents_h108.petwell.utils.ViewModelFactory
import com.ents_h108.petwell.view.viewmodel.AuthViewModel
import com.ents_h108.petwell.utils.Result
import com.ents_h108.petwell.utils.Utils.showToast

class ForgotPasswordFragment : Fragment() {
    private lateinit var binding: FragmentForgotPasswordBinding
    private val authViewModel: AuthViewModel by viewModels { ViewModelFactory() }
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

        binding.resetBtn.setOnClickListener {
            validateAndResetPassword(token)
        }
        binding.backBtn.setOnClickListener {
            findNavController().navigate(ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToLoginFragment())
        }

        observeResetPasswordResult()
    }

    private fun validateAndResetPassword(token: String) {
        val newPassword = binding.etNewPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()

        when {
            newPassword.isEmpty() -> {
                Toast.makeText(requireContext(), getString(R.string.field_empty), Toast.LENGTH_SHORT).show()
            }
            confirmPassword.isEmpty() -> {
                Toast.makeText(requireContext(), getString(R.string.field_empty), Toast.LENGTH_SHORT).show()
            }
            newPassword != confirmPassword -> {
                Toast.makeText(requireContext(), getString(R.string.cpassword_not_match), Toast.LENGTH_SHORT).show()
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
                    findNavController().navigate(ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToLoginFragment())
                }
                is Result.Error -> {
                    binding.loading.visibility = View.GONE
                    showToast(requireContext(), getString(R.string.token_mismatch))
                }
            }
        }
    }
}
