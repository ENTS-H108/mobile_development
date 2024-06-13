package com.ents_h108.petwell.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ents_h108.petwell.R
import com.ents_h108.petwell.data.model.History
import com.ents_h108.petwell.databinding.ItemHistoryBinding

class HistoryAdapter(private val listener: OnItemClickListener) :
    ListAdapter<History, HistoryAdapter.HistoryViewHolder>(DIFF_CALLBACK) {

    interface OnItemClickListener {
        fun onItemClick(item: History)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding =
            ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class HistoryViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val historyItem = getItem(position)
                    listener.onItemClick(historyItem)
                }
            }
        }

        fun bind(item: History) {
            binding.apply {
                when (item.type) {
                    1 -> tvHistoryType.text =
                        root.context.getString(R.string.consultation_title)
                    2 -> tvHistoryType.text =
                        root.context.getString(R.string.appointment_title)
                    else -> tvHistoryType.text = root.context.getString(R.string.scan_title)
                }
                tvTanggal.text = item.timestamp
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<History>() {
            override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
                return oldItem.timestamp == newItem.timestamp
            }

            override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
                return oldItem == newItem
            }
        }
    }
}
