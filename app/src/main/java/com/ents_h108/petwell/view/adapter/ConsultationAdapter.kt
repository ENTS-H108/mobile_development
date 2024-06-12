package com.ents_h108.petwell.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ents_h108.petwell.data.model.Doctor
import com.ents_h108.petwell.databinding.ItemChatPersonBinding


class ConsultationAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Doctor, ConsultationAdapter.ConsultaionViewHolder>(DIFF_CALLBACK) {

    interface OnItemClickListener {
        fun onItemClick(item: Doctor)
        fun onBtnClick(item: Doctor)

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

        fun bind(item: Doctor) {
            binding.apply {
                tvDoctorName.text = item.namadokter
                tvHospitalName.text = item.tempatbekerja
                doctorType.text = item.spesialis
                tvExperience.text = item.pengalaman
                tvPrice.text = item.harga
                imgDoctor.setImageResource(item.image)

            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Doctor>() {
            override fun areItemsTheSame(
                oldItem: Doctor,
                newItem: Doctor
            ): Boolean {
                return oldItem.namadokter == newItem.namadokter && oldItem.tempatbekerja == newItem.tempatbekerja
            }

            override fun areContentsTheSame(
                oldItem: Doctor,
                newItem: Doctor
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
