package com.ents_h108.petwell.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ents_h108.petwell.data.model.Doctor
import com.ents_h108.petwell.databinding.ItemAppointmentBinding


class AppointmentAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Doctor, AppointmentAdapter.AppointmentViewHolder>(DIFF_CALLBACK) {

    interface OnItemClickListener {
        fun onItemClick(item: Doctor)
        fun onBtnClick(item: Doctor)

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
                    val Doctor = getItem(position)
                    listener.onItemClick(Doctor)
                }
            }

            binding.btnAppointment.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val Doctor = getItem(position)
                    listener.onBtnClick(Doctor)
                }
            }


        }

        fun bind(item: Doctor) {
            binding.apply {
                tvDoctorName.text = item.namadokter
                tvHospitalName.text = item.tempatbekerja
                doctorType.text = item.spesialis
                tvLocation.text = item.pengalaman
                tvPrice.text = item.harga
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
