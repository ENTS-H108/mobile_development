package com.ents_h108.petwell.view.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.ents_h108.petwell.data.model.Pet
import com.ents_h108.petwell.data.repository.UserPreferences
import com.ents_h108.petwell.databinding.FragmentProfileBinding
import com.ents_h108.petwell.utils.Result
import com.ents_h108.petwell.utils.Utils.showToast
import com.ents_h108.petwell.view.adapter.PetAdapter
import com.ents_h108.petwell.view.viewmodel.AuthViewModel
import com.ents_h108.petwell.view.viewmodel.MainViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.viewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var petAdapter: PetAdapter
    private val authViewModel: AuthViewModel by viewModel()
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPetRecyclerView()
        setFragmentNavigation()
    }

    private fun setupPetRecyclerView() {
        val petActive = runBlocking {
            UserPreferences.getInstance(requireActivity().dataStore).getPetActive().first()
        }
        petAdapter = PetAdapter(object : PetAdapter.OnItemClickListener {
            override fun onItemClick(item: Pet) {
                mainViewModel.setPetActive(item.id)
            }

            override fun onEditProfileClick(item: Pet) {
                item.id.let {
                    findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToEditPetFragment(item))
                }
            }
        }, petActive)

        binding.rvPet.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = petAdapter
        }

        mainViewModel.getPets().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.petItemLoading.visibility = View.VISIBLE
                    binding.rvPet.visibility = View.GONE
                    setConstraintsForLoading(true)
                }

                is Result.Success -> {
                    binding.petItemLoading.visibility = View.GONE
                    binding.rvPet.visibility = View.VISIBLE
                    setConstraintsForLoading(false)
                    petAdapter.submitList(result.data)
                }

                is Result.Error -> {
                    binding.petItemLoading.visibility = View.GONE
                    setConstraintsForLoading(false)
                    context?.let { showToast(it, result.error) }
                }
            }
        }
    }

    private fun setFragmentNavigation() {
        binding.apply {
            mainViewModel.fetchUserProfile().observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Success -> {
                        binding.tvDeskripsi.text = result.data.email
                        binding.tvTitle.text = result.data.username
                        binding.imgProfile.load(result.data.profilePict)
                    }
                    is Result.Error -> {
                        showToast(requireContext(), "Error Authentication")
                    }
                    is Result.Loading -> {
                        // Handle loading state if needed
                    }
                }
            }
            btnSetting.setOnClickListener {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
            tvLogOut.setOnClickListener {
                lifecycleScope.launch {
                    authViewModel.logout()
                    findNavController().navigate(ProfileFragmentDirections.actionProfileToLogin())
                }
            }
            changePw.setOnClickListener {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToChangePasswordFragment())
            }
            btnHelp.setOnClickListener {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToHelpFragment())
            }
            btnHistory.setOnClickListener {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToHistoryFragment())
            }
            tvEdit.setOnClickListener {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment())
            }
            addPet.setOnClickListener {
                val pet: Pet? = null
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToEditPetFragment(pet))
            }
        }
    }

    private fun setConstraintsForLoading(loading: Boolean) {
        val params = binding.tvTitleOther.layoutParams as ConstraintLayout.LayoutParams
        params.topToBottom = if (loading) binding.petItemLoading.id else binding.rvPet.id
        binding.tvTitleOther.layoutParams = params
    }
}
