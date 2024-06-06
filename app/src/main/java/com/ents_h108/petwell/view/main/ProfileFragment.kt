package com.ents_h108.petwell.view.main

import PetAdapter
import PetItem
import android.content.Context
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
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ents_h108.petwell.data.repository.UserPreferences
import com.ents_h108.petwell.databinding.FragmentProfileBinding
import com.ents_h108.petwell.utils.ViewModelFactory
import com.ents_h108.petwell.view.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var petAdapter: PetAdapter
    private val viewModel: AuthViewModel by viewModels { ViewModelFactory(UserPreferences.getInstance(requireActivity().application.dataStore)) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button2.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
        binding.tvLogOut.setOnClickListener {
            lifecycleScope.launch {
                viewModel.logout()
                findNavController().navigate(ProfileFragmentDirections.actionProfileToLogin())
            }
        }

        val petList = listOf(
            PetItem("https://images.unsplash.com/photo-1606062663931-277af9e93298?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", "Nama Hewan 1", "Ras Hewan 1"),
            PetItem("https://plus.unsplash.com/premium_photo-1676479610722-1f855a4f0cac?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", "Nama Hewan 2", "Ras Hewan 2")
        )

        petAdapter = PetAdapter(object : PetAdapter.OnItemClickListener {
            override fun onItemClick(item: PetItem) {
                // Handle item click if needed
            }
        })

        binding.rvPet.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = petAdapter
        }
        petAdapter.submitList(petList)
    }
}