package com.ents_h108.petwell.view.main.articleTypes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ents_h108.petwell.data.model.Article
import com.ents_h108.petwell.databinding.FragmentListArticleBinding
import com.ents_h108.petwell.utils.Result
import com.ents_h108.petwell.view.adapter.ArticleWideAdapter
import com.ents_h108.petwell.view.main.PromoFragmentDirections
import com.ents_h108.petwell.view.viewmodel.MainViewModel
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
            when (result) {
                is Result.Loading -> {
                    binding.historyLoading.visibility = View.VISIBLE
                    binding.rvArticle.visibility = View.GONE
                }
                is Result.Success -> {
                    binding.historyLoading.visibility = View.GONE
                    binding.rvArticle.visibility = View.VISIBLE
                    articleAdapter.submitData(lifecycle, result.data)
                }
                is Result.Error -> {
                    binding.historyLoading.visibility = View.GONE
                    binding.rvArticle.visibility = View.GONE
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
