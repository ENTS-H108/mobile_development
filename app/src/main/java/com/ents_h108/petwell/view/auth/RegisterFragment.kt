package com.ents_h108.petwell.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ents_h108.petwell.utils.Result
import com.ents_h108.petwell.R
import com.ents_h108.petwell.data.model.MessageResponse
import com.ents_h108.petwell.databinding.FragmentRegisterBinding
import com.ents_h108.petwell.utils.Utils.resetError
import com.ents_h108.petwell.utils.Utils.showError
import com.ents_h108.petwell.utils.Utils.showToast
import com.ents_h108.petwell.view.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

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
            logBtn.setOnClickListener { navigateToLogin() }
            backBtn.setOnClickListener { navigateToOnboarding() }
        }
    }

    private fun navigateToLogin() {
        findNavController().navigate(RegisterFragmentDirections.actionRegisterToLogin())
    }

    private fun navigateToOnboarding() {
        findNavController().navigate(RegisterFragmentDirections.actionRegisterToOnboarding())
    }

    private fun handleRegister() {
        val fields = mapOf(
            binding.etUsernameRegister to binding.etUsernameRegister.text.toString().trim(),
            binding.etEmailRegister to binding.etEmailRegister.text.toString().trim(),
            binding.etPasswordRegister to binding.etPasswordRegister.text.toString().trim(),
            binding.etCpasswordRegister to binding.etCpasswordRegister.text.toString().trim()
        )

        if (fields.any { it.value.isEmpty() }) {
            fields.forEach { (editText, value) ->
                if (value.isEmpty()) showError(editText, requireContext()) else resetError(editText, requireContext())
            }
            showToast(requireContext(), getString(R.string.field_empty))
            return
        }

        if (!binding.etEmailRegister.isEmailValid) {
            showToast(requireContext(), getString(R.string.incorrect_email_format))
            return
        }

        if (!binding.etPasswordRegister.isPasswordValid) {
            showToast(requireContext(), getString(R.string.incorrect_pw_format))
            return
        }

        if (binding.etPasswordRegister.text.toString() != binding.etCpasswordRegister.text.toString()) {
            showError(binding.etCpasswordRegister, requireContext())
            showToast(requireContext(), getString(R.string.cpassword_not_match))
            return
        }

        authViewModel.register(
            fields.getValue(binding.etUsernameRegister),
            fields.getValue(binding.etEmailRegister),
            fields.getValue(binding.etPasswordRegister)
        ).observe(viewLifecycleOwner) { result ->
            handleRegistrationResult(result)
        }
    }

    private fun handleRegistrationResult(result: Result<MessageResponse>) {
        binding.loadingRegister.visibility = View.GONE
        when (result) {
            is Result.Success ->{
                showToast(requireContext(), result.data.message)
                binding.registerResult.text = result.data.message
            }
            is Result.Error -> {
                showToast(requireContext(), result.error)
                resetFields()
            }
            is Result.Loading -> binding.loadingRegister.visibility = View.VISIBLE
        }
    }

    private fun resetFields() {
        binding.etEmailRegister.text?.clear()
        binding.etPasswordRegister.text?.clear()
        binding.etCpasswordRegister.text?.clear()
    }
}
