package com.ents_h108.petwell.view.main.profile

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import coil.load
import com.ents_h108.petwell.databinding.FragmentEditProfileBinding
import com.ents_h108.petwell.utils.Result
import com.ents_h108.petwell.utils.Utils.showToast
import com.ents_h108.petwell.view.viewmodel.MainViewModel
import com.google.firebase.storage.FirebaseStorage
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val IMAGE_TYPE = "image/*"

class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding
    private val mainViewModel: MainViewModel by viewModel()
    private var email: String? = null
    private var storageRef = FirebaseStorage.getInstance().reference
    private var selectedImageUri: Uri? = null

    private val getImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            selectedImageUri = it
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
                    binding.loading.visibility = View.GONE
                    val user = result.data
                    email = user.email
                    binding.etEdtUsername.setText(user.username)
                    binding.imgAvatar.load(user.profilePict)
                }
                is Result.Error -> {
                    binding.loading.visibility = View.GONE
                    showToast(requireContext(), result.error)
                }
                is Result.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                }
            }
        }

        binding.saveBtn.setOnClickListener {
            val imageUrl = selectedImageUri?.let { uri ->
                uploadImage(uri)
            }

            mainViewModel.editProfile(binding.etEdtUsername.text.toString(), imageUrl)
                .observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Result.Loading -> binding.loading.visibility = View.VISIBLE
                        is Result.Success -> {
                            binding.loading.visibility = View.GONE
                            showToast(requireContext(), "Profile updated successfully")
                            findNavController().popBackStack()
                        }
                        is Result.Error -> {
                            binding.loading.visibility = View.GONE
                            showToast(requireContext(), result.error)
                            Log.e("Edit Profile", "API call failed: ${result.error}")
                        }
                    }
                }
        }
    }

    private fun uploadImage(uri: Uri): String? {
        var imageUrl: String? = null
        email?.let { email ->
            val profileRef = storageRef.child("profile_images/$email")
            profileRef.putFile(uri)
                .addOnSuccessListener {
                    profileRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                        imageUrl = downloadUrl.toString()
                    }
                }
                .addOnFailureListener { exception ->
                    binding.loading.visibility = View.GONE
                    showToast(requireContext(), "Image upload failed: ${exception.message}")
                }
        } ?: showToast(requireContext(), "Email not found")
        return imageUrl
    }
}
