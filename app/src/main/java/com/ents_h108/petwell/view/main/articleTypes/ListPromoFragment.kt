package com.ents_h108.petwell.view.main.articleTypes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.ents_h108.petwell.data.model.Article
import com.ents_h108.petwell.databinding.FragmentListPromoBinding
import com.ents_h108.petwell.view.adapter.PromoAdapter
import com.ents_h108.petwell.view.main.PromoFragmentDirections
import com.ents_h108.petwell.view.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListPromoFragment : Fragment() {

    private lateinit var binding: FragmentListPromoBinding
    private lateinit var promoAdapter: PromoAdapter
    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentListPromoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observePromo()
    }

    private fun setupRecyclerView() {
        promoAdapter = PromoAdapter(object : PromoAdapter.OnItemClickListener {
            override fun onItemClick(item: Article) {
                findNavController().navigate(PromoFragmentDirections.actionPromoFragmentToDetailPromoArticleFragment(item))
            }
        })
        binding.rvListPromo.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = promoAdapter
        }
    }

    private fun observePromo() {
        viewModel.getContent("promo").observe(viewLifecycleOwner) { pagingData ->
            promoAdapter.submitData(lifecycle, pagingData)
        }
        lifecycleScope.launch {
            promoAdapter.loadStateFlow.collectLatest { loadState ->
                binding.promoLoading.isVisible = loadState.refresh is LoadState.Loading
                binding.rvListPromo.isVisible = loadState.refresh is LoadState.NotLoading
                binding.rvListPromo.isGone = loadState.refresh is LoadState.Error
                if (loadState.refresh is LoadState.Error) {
                    val errorState = loadState.refresh as LoadState.Error
                    Toast.makeText(context, errorState.error.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
