package com.ents_h108.petwell.view.main

import android.content.Context
import com.ents_h108.petwell.view.adapter.PetAdapter
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ents_h108.petwell.data.model.Pet
import com.ents_h108.petwell.data.repository.UserPreferences
import com.ents_h108.petwell.databinding.FragmentProfileBinding
import com.ents_h108.petwell.utils.Result
import com.ents_h108.petwell.utils.Utils
import com.ents_h108.petwell.view.viewmodel.AuthViewModel
import com.ents_h108.petwell.view.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var petAdapter: PetAdapter
    private val authViewModel: AuthViewModel by viewModel()
    private val mainViewModel: MainViewModel by viewModel()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coroutineScope.launch {
            binding.tvTitle.text = UserPreferences.getInstance(requireActivity().dataStore).getUsername().first()
            binding.tvDeskripsi.text = UserPreferences.getInstance(requireActivity().dataStore).getEmail().first()
        }

        binding.btnSetting.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
        binding.tvLogOut.setOnClickListener {
            lifecycleScope.launch {
                authViewModel.logout()
                findNavController().navigate(ProfileFragmentDirections.actionProfileToLogin())
            }
        }

        petAdapter = PetAdapter(object : PetAdapter.OnItemClickListener {
            override fun onItemClick(item: Pet) {
                // Handle item click if needed
            }
            override fun onEditProfileClick(item: Pet) {
                findNavController().navigate(ProfileFragmentDirections
                    .actionProfileFragmentToEditPetFragment(item))
            }
        })

        binding.rvPet.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = petAdapter
        }

        mainViewModel.getPets().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.petItemLoading.visibility = View.VISIBLE
                    binding.rvPet.visibility = View.GONE
                }

                is Result.Success -> {
                    binding.petItemLoading.visibility = View.GONE
                    binding.rvPet.visibility = View.VISIBLE
                    petAdapter.submitList(result.data)
                }

                is Result.Error -> {
                    binding.petItemLoading.visibility = View.GONE
                    context?.let { Utils.showToast(it, result.error) }
                }
            }
        }

        navigationFragment()
    }

    private fun navigationFragment() {
        binding.apply {
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
}
