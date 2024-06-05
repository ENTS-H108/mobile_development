package com.ents_h108.petwell.view.main.promo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ents_h108.petwell.data.model.Article
import com.ents_h108.petwell.databinding.FragmentListArticleBinding
import com.ents_h108.petwell.utils.Result
import com.ents_h108.petwell.utils.ViewModelFactory
import com.ents_h108.petwell.view.adapter.ArticleWideAdapter
import com.ents_h108.petwell.view.viewmodel.MainViewModel

class ListArticleFragment : Fragment() {
    private lateinit var binding: FragmentListArticleBinding
    private lateinit var articleAdapter: ArticleWideAdapter
    private val viewModel: MainViewModel by viewModels { ViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        articleAdapter = ArticleWideAdapter(object : ArticleWideAdapter.OnItemClickListener {
            override fun onItemClick(item: Article) {
                // Click handler
            }
        })

        binding.rvArticle.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = articleAdapter
        }

        observeArticleData()
        viewModel.getArticles()
    }

    private fun observeArticleData() {
        viewModel.articles.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    Log.d("ListArticleFragment", "Loading")
                    binding.historyLoading.visibility = View.VISIBLE
                    binding.rvArticle.visibility = View.GONE
                }

                is Result.Success -> {
                    Log.d("ListArticleFragment", "Success")
                    binding.historyLoading.visibility = View.GONE
                    binding.rvArticle.visibility = View.VISIBLE
                    articleAdapter.submitList(result.data)
                }

                is Result.Error -> {
                    Log.d("ListArticleFragment", "Error")
                    binding.historyLoading.visibility = View.GONE
                    Toast.makeText(context, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
