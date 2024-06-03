package com.ents_h108.petwell.view.main.promo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.FragmentListArticleBinding
import com.ents_h108.petwell.view.adapter.ArticleWideAdapterAdapter
import com.ents_h108.petwell.view.adapter.ArticleItem


class ListArticleFragment : Fragment() {
    private lateinit var binding: FragmentListArticleBinding
    private lateinit var articleAdapter: ArticleWideAdapterAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageArticleUrls = listOf(
            R.drawable.img_article2,
            R.drawable.img_article1,
            R.drawable.img_article2,
            R.drawable.img_article1,
            R.drawable.img_article2,
            R.drawable.img_article1
        )
        val articleItems = imageArticleUrls.map { ArticleItem(it) }

        articleAdapter = ArticleWideAdapterAdapter(object : ArticleWideAdapterAdapter.OnItemClickListener {
            override fun onItemClick(item: ArticleItem) {
                // Handle item click if needed
            }
        })

        binding.rvArticle.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = articleAdapter
        }
        articleAdapter.submitList(articleItems)
    }

}