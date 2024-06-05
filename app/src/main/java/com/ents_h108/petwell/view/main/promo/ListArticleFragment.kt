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

        articleAdapter =
            ArticleWideAdapter(object : ArticleWideAdapter.OnItemClickListener {
                override fun onItemClick(item: Article) {
                    //click handler
                }
            })
        val articleList = listOf(
            Article("1", "Title 1", "Description 1", "thumbnail_url_1", "type_1"),
            Article("2", "Title 2", "Description 2", "thumbnail_url_2", "type_2"),
            Article("3", "Title 3", "Description 3", "thumbnail_url_3", "type_3"),
        )
        Log.d("ListArticleFragment", articleList.toString())


        binding.rvArticle.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = articleAdapter
        }

        observeArticleData()

    }

    private fun observeArticleData() {
        viewModel.articles.observe(viewLifecycleOwner) { result ->
            Log.d("ListArtikel", viewModel.getArticle().toString())
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