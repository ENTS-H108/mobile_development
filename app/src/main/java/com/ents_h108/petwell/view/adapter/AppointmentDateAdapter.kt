package com.ents_h108.petwell.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ents_h108.petwell.data.model.WorkSchedule
import com.ents_h108.petwell.databinding.ItemAppointmentDateBinding

class AppointmentDateAdapter :
    ListAdapter<WorkSchedule, AppointmentDateAdapter.AppointmentDateViewHolder>(DIFF_CALLBACK) {

    interface OnItemClickListener {
        fun onItemClick(item: WorkSchedule)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentDateViewHolder {
        val binding = ItemAppointmentDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AppointmentDateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AppointmentDateViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AppointmentDateViewHolder(private val binding: ItemAppointmentDateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WorkSchedule) {
            binding.apply {
                tvDate.text = item.date
                rvHour.layoutManager = LinearLayoutManager(rvHour.context, LinearLayoutManager.HORIZONTAL, false)
                val hourAdapter = AppointmentHourAdapter()
                rvHour.adapter = hourAdapter
                hourAdapter.submitList(item.workHours)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<WorkSchedule>() {
            override fun areItemsTheSame(oldItem: WorkSchedule, newItem: WorkSchedule): Boolean {
                return oldItem.date == newItem.date
            }

            override fun areContentsTheSame(oldItem: WorkSchedule, newItem: WorkSchedule): Boolean {
                return oldItem == newItem
            }
        }
    }
}
