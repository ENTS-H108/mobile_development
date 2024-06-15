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
import androidx.lifecycle.lifecycleScope
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
import kotlinx.coroutines.launch
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
        setupAdapters()
        setupRecyclerViews()
        setupNavigation()
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            setupLocation(requireContext(), fusedLocationProviderClient) { city ->
                locationTitle.text = city
            }
            with(viewModel){
                fetchUserProfile().observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Result.Success -> {
                            profileUserName.text = result.data.username
                            profileImage.load(result.data.profilePict)
                        }
                        is Result.Error -> {
                            showToast(requireContext(), "Error Authentication")
                        }
                        is Result.Loading -> {
                        }
                    }
                }
                getContent("promo").observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Result.Loading -> {
                            promoLoading.visibility = View.VISIBLE
                            rvPromo.visibility = View.GONE
                        }
                        is Result.Success -> {
                            promoLoading.visibility = View.GONE
                            rvPromo.visibility = View.VISIBLE
                            promoAdapter.submitData(lifecycle, result.data)
                        }
                        is Result.Error -> {
                            promoLoading.visibility = View.GONE
                            rvPromo.visibility = View.GONE
                            Toast.makeText(context, result.error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                getContent("artikel").observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Result.Loading -> {
                            articleLoading.visibility = View.VISIBLE
                            rvArticle.visibility = View.GONE
                        }
                        is Result.Success -> {
                            articleLoading.visibility = View.GONE
                            rvArticle.visibility = View.VISIBLE
                            articleAdapter.submitData(lifecycle, result.data)
                        }
                        is Result.Error -> {
                            articleLoading.visibility = View.GONE
                            rvArticle.visibility = View.GONE
                            Toast.makeText(context, result.error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                lifecycleScope.launch {
                    val petActive = UserPreferences.getInstance(requireActivity().dataStore).getPetActive().first()
                    petActive?.let { petId ->
                        getPet(petId).observe(viewLifecycleOwner) { result ->
                            when (result) {
                                is Result.Loading -> {}
                                is Result.Success -> {
                                    petImage.load(if (result.data.species == "anjing") R.drawable.avatar_dog else R.drawable.avatar_cat)
                                    petName.text = result.data.name
                                    petRace.text = result.data.species
                                }
                                is Result.Error -> {
                                    Log.d("HomeFragment", "Pet Name: ${result.error}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupAdapters() {
        promoAdapter = PromoAdapter(object : PromoAdapter.OnItemClickListener {
            override fun onItemClick(item: Article) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailArticlePromoFragment(item))
            }
        })

        articleAdapter = ArticleAdapter(object : ArticleAdapter.OnItemClickListener {
            override fun onItemClick(item: Article) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailArticlePromoFragment(item))
            }
        })
    }

    private fun setupRecyclerViews() {
        binding.apply {
            rvPromo.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = promoAdapter
            }
            rvArticle.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = articleAdapter
            }
        }
    }

    private fun setupNavigation() {
        binding.apply {
            with(findNavController()) {
                tvPromoMore.setOnClickListener { navigate(HomeFragmentDirections.actionHomeFragmentToPromoFragment(0)) }
                tvArticleMore.setOnClickListener { navigate(HomeFragmentDirections.actionHomeFragmentToPromoFragment(1)) }
                btnConsultation.setOnClickListener { navigate(HomeFragmentDirections.actionHomeFragmentToConsultationFragment()) }
                btnScan.setOnClickListener { navigate(HomeFragmentDirections.actionHomeFragmentToChosePetFragment()) }
                btnAppointment.setOnClickListener { navigate(HomeFragmentDirections.actionHomeFragmentToAppointmentFragment()) }
            }
        }
    }
}
