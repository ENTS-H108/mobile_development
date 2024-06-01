package com.ents_h108.petwell.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.FragmentHomeBinding
import com.ents_h108.petwell.view.adapter.ArticleAdapter
import com.ents_h108.petwell.view.adapter.PromoAdapter
import com.ents_h108.petwell.view.adapter.ArticleItem

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var promoAdapter: PromoAdapter
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageUrls = listOf(
            R.drawable.img_promo1,
            R.drawable.img_promo2
        )
        val promoItems = imageUrls.map { ArticleItem(it) }
        promoAdapter = PromoAdapter(object : PromoAdapter.OnItemClickListener {
            override fun onItemClick(item: ArticleItem) {
                // Handle item click if needed
            }
        })

        val imageArticleUrls = listOf(
            R.drawable.img_article1,
            R.drawable.img_article2
        )
        val articleItems = imageArticleUrls.map { ArticleItem(it) }

        articleAdapter = ArticleAdapter(object : ArticleAdapter.OnItemClickListener {
            override fun onItemClick(item: ArticleItem) {
                // Handle item click if needed
            }
        })
        binding.rvPromo.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = promoAdapter
        }
        binding.rvArticle.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = articleAdapter
        }
        promoAdapter.submitList(promoItems)
        articleAdapter.submitList(articleItems)
    }
}
