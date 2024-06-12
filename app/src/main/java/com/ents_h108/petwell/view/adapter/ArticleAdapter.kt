package com.ents_h108.petwell.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ents_h108.petwell.data.model.Article
import com.ents_h108.petwell.databinding.ItemArticleBinding

class ArticleAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<Article, ArticleAdapter.ArticleViewHolder>(DIFF_CALLBACK) {

    interface OnItemClickListener {
        fun onItemClick(item: Article)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ArticleViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                @Suppress("DEPRECATION") val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val articleItem = getItem(position)
                    if (articleItem != null) {
                        listener.onItemClick(articleItem)
                    }
                }
            }
        }

        fun bind(item: Article) {
            binding.itemThumbnail.load(item.thumbnail)
            binding.articleTitle.text = item.title
            binding.articleDesc.text = item.description
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }
}