package com.ents_h108.petwell.view.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.FragmentRegisterBinding
import com.ents_h108.petwell.utils.Utils.resetError
import com.ents_h108.petwell.utils.Utils.showError
import com.ents_h108.petwell.utils.Utils.showToast

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            binding.regBtn.setOnClickListener {
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
                        showToast(requireContext(), getString(R.string.register_succes))
                        findNavController().navigate(RegisterFragmentDirections.actionRegisterToLogin())
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
}