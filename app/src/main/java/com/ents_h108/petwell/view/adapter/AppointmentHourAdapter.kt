package com.ents_h108.petwell.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ents_h108.petwell.R
import com.ents_h108.petwell.data.model.WorkHours
import com.ents_h108.petwell.databinding.ItemAppointmentHourBinding

class AppointmentHourAdapter(private val listener: OnItemClickListener) :
    ListAdapter<WorkHours, AppointmentHourAdapter.AppointmentHourViewHolder>(DIFF_CALLBACK) {

    interface OnItemClickListener {
        fun onItemClick(item: WorkHours)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentHourViewHolder {
        val binding = ItemAppointmentHourBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AppointmentHourViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AppointmentHourViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AppointmentHourViewHolder(private val binding: ItemAppointmentHourBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WorkHours) {
            binding.apply {
                rgHour.text = item.availSlot
                root.setOnClickListener {
                    listener.onItemClick(item)
                }
                rgHour.setBackgroundResource(if (item.isAvail) R.drawable.bg_consultation_hour else R.drawable.btn_yellow_disable)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<WorkHours>() {
            override fun areItemsTheSame(oldItem: WorkHours, newItem: WorkHours): Boolean {
                return oldItem.availSlot == newItem.availSlot
            }

            override fun areContentsTheSame(oldItem: WorkHours, newItem: WorkHours): Boolean {
                return oldItem == newItem
            }
        }
    }
}