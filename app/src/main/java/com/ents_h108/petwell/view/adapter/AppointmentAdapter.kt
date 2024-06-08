package com.ents_h108.petwell.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ents_h108.petwell.databinding.ItemAppointmentBinding

data class AppointmentItem(
    val namadokter: String,
    val tempatbekerja: String,
    val spesialis: String,
    val lokasi: String,
    val harga: String
)

class AppointmentAdapter(private val listener: OnItemClickListener) :
    ListAdapter<AppointmentItem, AppointmentAdapter.AppointmentViewHolder>(DIFF_CALLBACK) {

    interface OnItemClickListener {
        fun onItemClick(item: AppointmentItem)
        fun onBtnClick(item: AppointmentItem)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val binding =
            ItemAppointmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AppointmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AppointmentViewHolder(private val binding: ItemAppointmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val appointmentItem = getItem(position)
                    listener.onItemClick(appointmentItem)
                }
            }

            binding.btnAppointment.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val appointmentItem = getItem(position)
                    listener.onBtnClick(appointmentItem)
                }
            }


        }

        fun bind(item: AppointmentItem) {
            binding.apply {
                tvDoctorName.text = item.namadokter
                tvHospitalName.text = item.tempatbekerja
                doctorType.text = item.spesialis
                tvLocation.text = item.lokasi
                tvPrice.text = item.harga
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AppointmentItem>() {
            override fun areItemsTheSame(
                oldItem: AppointmentItem,
                newItem: AppointmentItem
            ): Boolean {
                return oldItem.namadokter == newItem.namadokter && oldItem.tempatbekerja == newItem.tempatbekerja
            }

            override fun areContentsTheSame(
                oldItem: AppointmentItem,
                newItem: AppointmentItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
