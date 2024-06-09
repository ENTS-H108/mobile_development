package com.ents_h108.petwell.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ents_h108.petwell.data.dataClassDummy.ConsultationItem
import com.ents_h108.petwell.databinding.ItemChatPersonBinding


class ConsultationAdapter(private val listener: OnItemClickListener) :
    ListAdapter<ConsultationItem, ConsultationAdapter.ConsultaionViewHolder>(DIFF_CALLBACK) {

    interface OnItemClickListener {
        fun onItemClick(item: ConsultationItem)
        fun onBtnClick(item: ConsultationItem)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsultaionViewHolder {
        val binding =
            ItemChatPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConsultaionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConsultaionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ConsultaionViewHolder(private val binding: ItemChatPersonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val appointmentItem = getItem(position)
                    listener.onItemClick(appointmentItem)
                }
            }

            binding.btnChat.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val appointmentItem = getItem(position)
                    listener.onBtnClick(appointmentItem)
                }
            }


        }

        fun bind(item: ConsultationItem) {
            binding.apply {
                tvDoctorName.text = item.namadokter
                tvHospitalName.text = item.tempatbekerja
                doctorType.text = item.spesialis
                tvExperience.text = item.Pengalaman
                tvPrice.text = item.harga

            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ConsultationItem>() {
            override fun areItemsTheSame(
                oldItem: ConsultationItem,
                newItem: ConsultationItem
            ): Boolean {
                return oldItem.namadokter == newItem.namadokter && oldItem.tempatbekerja == newItem.tempatbekerja
            }

            override fun areContentsTheSame(
                oldItem: ConsultationItem,
                newItem: ConsultationItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
