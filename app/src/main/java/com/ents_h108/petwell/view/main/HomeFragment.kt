package com.ents_h108.petwell.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ents_h108.petwell.data.model.Article
import com.ents_h108.petwell.databinding.FragmentHomeBinding
import com.ents_h108.petwell.utils.Result
import com.ents_h108.petwell.view.adapter.ArticleAdapter
import com.ents_h108.petwell.view.adapter.PromoAdapter
import com.ents_h108.petwell.view.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var promoAdapter: PromoAdapter
    private lateinit var articleAdapter: ArticleAdapter
    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapters()
        setupRecyclerViews()
        observeArticleData()
        setupNavigation()
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
        viewModel.getPromo().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.promoLoading.visibility = View.VISIBLE
                    binding.rvPromo.visibility = View.GONE
                }

                is Result.Success -> {
                    binding.promoLoading.visibility = View.GONE
                    binding.rvPromo.visibility = View.VISIBLE
                    promoAdapter.submitList(result.data)
                }

                is Result.Error -> {
                    binding.promoLoading.visibility = View.GONE
                    binding.rvPromo.visibility = View.GONE
                    Toast.makeText(context, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.getArticles().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.articleLoading.visibility = View.VISIBLE
                    binding.rvArticle.visibility = View.GONE
                }

                is Result.Success -> {
                    binding.articleLoading.visibility = View.GONE
                    binding.rvArticle.visibility = View.VISIBLE
                    articleAdapter.submitList(result.data)
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