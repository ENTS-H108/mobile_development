package com.ents_h108.petwell.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ents_h108.petwell.data.model.Article
import com.ents_h108.petwell.databinding.ItemPromoBinding

class PromoAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<Article, PromoAdapter.PromoViewHolder>(DIFF_CALLBACK) {

    interface OnItemClickListener {
        fun onItemClick(item: Article)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromoViewHolder {
        val binding = ItemPromoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PromoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PromoViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class PromoViewHolder(private val binding: ItemPromoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val promoItem = getItem(position)
                    if (promoItem != null) {
                        listener.onItemClick(promoItem)
                    }
                }
            }
        }

        fun bind(item: Article) {
            binding.itemThumbnail.load(item.title)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }
}
