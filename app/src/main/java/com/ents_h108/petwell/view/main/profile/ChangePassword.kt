package com.ents_h108.petwell.view.main.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.FragmentChangePasswordBinding
import com.ents_h108.petwell.utils.Result
import com.ents_h108.petwell.utils.Utils.showError
import com.ents_h108.petwell.utils.Utils.showToast
import com.ents_h108.petwell.view.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangePassword : Fragment() {

    private lateinit var binding: FragmentChangePasswordBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.resetBtn.setOnClickListener { resetPassword() }
    }

    private fun resetPassword() {
        if (binding.etNewPassword.text.toString() != binding.etConfirmPassword.text.toString()) {
            showError(binding.etCurrentPassword, requireContext())
            showToast(requireContext(), getString(R.string.cpassword_not_match))
            return
        }

        viewModel.changePassword(binding.etCurrentPassword.text.toString(), binding.etNewPassword.text.toString()).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.loading.visibility = View.GONE
                    showToast(requireContext(), result.data.message ?: "Berhasil ubah password")
                    findNavController().popBackStack()
                }
                is Result.Error -> {
                    binding.loading.visibility = View.GONE
                    showToast(requireContext(), result.error)
                }
            }
        }
    }
}