package com.ents_h108.petwell.view.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.activity.result.contract.ActivityResultContracts
import coil.load
import com.ents_h108.petwell.databinding.FragmentEditProfileBinding
import com.ents_h108.petwell.utils.Result
import com.ents_h108.petwell.utils.Utils.showToast
import com.ents_h108.petwell.view.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val IMAGE_TYPE = "image/*"

class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding
    private val mainViewModel: MainViewModel by viewModel()

    private val getImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            binding.imgAvatar.load(it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnEditPicture.setOnClickListener { getImage.launch(IMAGE_TYPE) }
        mainViewModel.fetchUserProfile().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    val user = result.data
                    binding.etEdtUsername.setText(user.username)
                    binding.imgAvatar.load(user.profilePict)
                }
                is Result.Error -> {
                    showToast(requireContext(), result.error)
                }
                is Result.Loading -> {
                    // Handle loading state if needed
                }
            }
        }
        binding.saveBtn.setOnClickListener {
            mainViewModel.editProfile(binding.etEdtUsername.text.toString(),"test").observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Loading -> binding.loading.visibility = View.VISIBLE
                    is Result.Success -> {
                        binding.loading.visibility = View.GONE
                    }
                    is Result.Error -> {
                        binding.loading.visibility = View.GONE
                        context?.let { it2 -> showToast(it2, result.error) }
                    }
                }
            }
        }
    }
}
