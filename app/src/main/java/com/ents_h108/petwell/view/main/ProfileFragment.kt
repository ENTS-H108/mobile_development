package com.ents_h108.petwell.view.main

import com.ents_h108.petwell.view.adapter.PetAdapter
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ents_h108.petwell.data.model.Pet
import com.ents_h108.petwell.databinding.FragmentProfileBinding
import com.ents_h108.petwell.utils.Result
import com.ents_h108.petwell.view.viewmodel.AuthViewModel
import com.ents_h108.petwell.view.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

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
                // Navigate to HomeFragment with data
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToEditPetFragment())
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
                    Log.d("profile", result.data.toString())
                    petAdapter.submitList(result.data)
                }

                is Result.Error -> {
                    binding.petItemLoading.visibility = View.GONE
                    Toast.makeText(context, result.error, Toast.LENGTH_SHORT).show()
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
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToEditPetFragment())
            }
        }
    }
}
