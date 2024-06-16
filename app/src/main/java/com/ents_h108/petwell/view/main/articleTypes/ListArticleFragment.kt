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
import com.ents_h108.petwell.databinding.FragmentListArticleBinding
import com.ents_h108.petwell.utils.Result
import com.ents_h108.petwell.view.adapter.ArticleWideAdapter
import com.ents_h108.petwell.view.main.PromoFragmentDirections
import com.ents_h108.petwell.view.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListArticleFragment : Fragment() {
    private lateinit var binding: FragmentListArticleBinding
    private lateinit var articleAdapter: ArticleWideAdapter
    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentListArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeArticles()
    }

    private fun setupRecyclerView() {
        articleAdapter = ArticleWideAdapter(object : ArticleWideAdapter.OnItemClickListener {
            override fun onItemClick(item: Article) {
                findNavController().navigate(PromoFragmentDirections.actionPromoFragmentToDetailPromoArticleFragment(item))
            }
        })
        binding.rvArticle.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = articleAdapter
        }
    }

    private fun observeArticles() {
        viewModel.getContent("artikel").observe(viewLifecycleOwner) { result ->
            articleAdapter.submitData(lifecycle, result)
        }
        lifecycleScope.launch {
            articleAdapter.loadStateFlow.collectLatest { loadState ->
                binding.articleLoading.isVisible = loadState.refresh is LoadState.Loading
                binding.rvArticle.isVisible = loadState.refresh is LoadState.NotLoading
                binding.rvArticle.isGone = loadState.refresh is LoadState.Error
                if (loadState.refresh is LoadState.Error) {
                    val errorState = loadState.refresh as LoadState.Error
                    Toast.makeText(context, errorState.error.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
