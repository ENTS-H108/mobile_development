package com.ents_h108.petwell.view.main.promo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.FragmentListPromoBinding
import com.ents_h108.petwell.view.adapter.ArticleItem
import com.ents_h108.petwell.view.adapter.PromoAdapter


class ListPromoFragment : Fragment() {

    private lateinit var binding: FragmentListPromoBinding
    private lateinit var promoAdapter: PromoAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListPromoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageUrls = listOf(
            R.drawable.img_promo1,
            R.drawable.img_promo2,
            R.drawable.img_promo1,
            R.drawable.img_promo2,
            R.drawable.img_promo1,
            R.drawable.img_promo2
        )
        val promoItems = imageUrls.map { ArticleItem(it) }
        promoAdapter = PromoAdapter(object : PromoAdapter.OnItemClickListener {
            override fun onItemClick(item: ArticleItem) {
                // Handle item click if needed
            }
        })

        binding.rvListPromo.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = promoAdapter
        }

        promoAdapter.submitList(promoItems)
    }

}