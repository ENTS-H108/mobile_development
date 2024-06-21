package com.ents_h108.petwell.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ents_h108.petwell.R
import com.ents_h108.petwell.data.model.Doctor
import com.ents_h108.petwell.databinding.ItemAppointmentBinding
import com.ents_h108.petwell.utils.Utils.getAddressFromLocation

class AppointmentAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Doctor, AppointmentAdapter.AppointmentViewHolder>(DIFF_CALLBACK) {

    interface OnItemClickListener {
        fun onItemClick(item: Doctor)
        fun onBtnClick(item: Doctor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val binding = ItemAppointmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AppointmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AppointmentViewHolder(private val binding: ItemAppointmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(bindingAdapterPosition))
                }
            }

            binding.btnAppointment.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    listener.onBtnClick(getItem(bindingAdapterPosition))
                }
            }
        }

        fun bind(item: Doctor) {
            binding.apply {
                tvDoctorName.text = item.name
                tvHospitalName.text = item.hospital
                doctorType.text = item.type
                getAddressFromLocation(root.context, item.lat, item.long) { _, street,  number ->
                    tvLocation.text = root.context.getString(R.string.location_format, street ?: "", number ?: "")
                }
                tvPrice.text = item.price
                imgDoctor.load(item.profpict)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Doctor>() {
            override fun areItemsTheSame(oldItem: Doctor, newItem: Doctor): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Doctor, newItem: Doctor): Boolean {
                return oldItem == newItem
            }
        }
    }
}
