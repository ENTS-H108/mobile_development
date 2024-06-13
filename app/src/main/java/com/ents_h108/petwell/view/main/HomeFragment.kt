package com.ents_h108.petwell.view.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.ents_h108.petwell.R
import com.ents_h108.petwell.data.model.Article
import com.ents_h108.petwell.data.repository.UserPreferences
import com.ents_h108.petwell.databinding.FragmentHomeBinding
import com.ents_h108.petwell.utils.Result
import com.ents_h108.petwell.utils.Utils.setupLocation
import com.ents_h108.petwell.utils.Utils.showToast
import com.ents_h108.petwell.view.adapter.ArticleAdapter
import com.ents_h108.petwell.view.adapter.PromoAdapter
import com.ents_h108.petwell.view.viewmodel.MainViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.viewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var promoAdapter: PromoAdapter
    private lateinit var articleAdapter: ArticleAdapter
    private val viewModel: MainViewModel by viewModel()
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        setupUI()
        setupAdapters()
        setupRecyclerViews()
        observeArticleData()
        setupNavigation()
    }

    private fun setupUI() {
        setupLocation(requireContext(), fusedLocationProviderClient) { city ->
            binding.locationTitle.text = city
        }
        viewModel.fetchUserProfile().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    binding.profileUserName.text = result.data.username
                    binding.profileImage.load(result.data.profilePict)
                }
                is Result.Error -> {
                    showToast(requireContext(), "Error Authentication")
                }
                is Result.Loading -> {
                }
            }
        }
        val petActive = runBlocking {
            UserPreferences.getInstance(requireActivity().dataStore).getPetActive().first()
        }
        if (petActive != null) {
            viewModel.getPet(petActive).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Loading -> {}
                    is Result.Success -> {
                        binding.petImage.load(if (result.data.species == "anjing") R.drawable.avatar_dog else R.drawable.avatar_cat)
                        binding.petName.text = result.data.name
                        binding.petRace.text = result.data.species
                    }
                    is Result.Error -> {
                        Log.d("HomeFragment", "Pet Name: ${result.error}")
                    }
                }
            }
        }
    }

    private fun setupAdapters() {
        promoAdapter = PromoAdapter(object : PromoAdapter.OnItemClickListener {
            override fun onItemClick(item: Article) {
            }
        })

        articleAdapter = ArticleAdapter(object : ArticleAdapter.OnItemClickListener {
            override fun onItemClick(item: Article) {
            }
        })
    }

    private fun setupRecyclerViews() {
        binding.rvPromo.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = promoAdapter
        }
        binding.rvArticle.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = articleAdapter
        }
    }

    private fun observeArticleData() {
        viewModel.getContent("promo").observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.promoLoading.visibility = View.VISIBLE
                    binding.rvPromo.visibility = View.GONE
                }
                is Result.Success -> {
                    binding.promoLoading.visibility = View.GONE
                    binding.rvPromo.visibility = View.VISIBLE
                    promoAdapter.submitData(lifecycle, result.data)
                }
                is Result.Error -> {
                    binding.promoLoading.visibility = View.GONE
                    binding.rvPromo.visibility = View.GONE
                    Toast.makeText(context, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.getContent("artikel").observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.articleLoading.visibility = View.VISIBLE
                    binding.rvArticle.visibility = View.GONE
                }
                is Result.Success -> {
                    binding.articleLoading.visibility = View.GONE
                    binding.rvArticle.visibility = View.VISIBLE
                    articleAdapter.submitData(lifecycle, result.data)
                }
                is Result.Error -> {
                    binding.articleLoading.visibility = View.GONE
                    binding.rvArticle.visibility = View.GONE
                    Toast.makeText(context, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupNavigation() {
        binding.apply {
            tvPromoMore.setOnClickListener { findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPromoFragment(0)) }
            tvArticleMore.setOnClickListener { findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPromoFragment(1)) }
            btnConsultation.setOnClickListener { findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToConsultationFragment()) }
            btnScan.setOnClickListener { findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToChosePetFragment()) }
            btnAppointment.setOnClickListener { findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAppointmentFragment()) }
        }
    }
}
