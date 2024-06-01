package com.ents_h108.petwell.view.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.FragmentForgotPasswordBinding


class ForgotPasswordFragment : Fragment() {
    private lateinit var binding: FragmentForgotPasswordBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
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
    }

    private fun validateAndResetPassword() {
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
                Toast.makeText(requireContext(), getString(R.string.password_reset_success), Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
            }
        }
    }


}