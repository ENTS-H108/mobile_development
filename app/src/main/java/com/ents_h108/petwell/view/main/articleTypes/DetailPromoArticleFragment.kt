package com.ents_h108.petwell.view.main.articleTypes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.ents_h108.petwell.data.model.Article
import com.ents_h108.petwell.databinding.FragmentDetailPromoArticleBinding

class DetailPromoArticleFragment : Fragment() {

    private lateinit var binding: FragmentDetailPromoArticleBinding
    private var detail: Article? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailPromoArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            detail = DetailPromoArticleFragmentArgs.fromBundle(it).detail
        }
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            detail?.let { article ->
                tvTitle.text = article.title
                tvDescription.text = article.desc
                ivThumbnail.load(article.thumbnail)
            }
        }
    }
}