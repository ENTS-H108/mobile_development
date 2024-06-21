package com.ents_h108.petwell.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ents_h108.petwell.data.model.WorkHours
import com.ents_h108.petwell.data.model.WorkSchedule
import com.ents_h108.petwell.databinding.ItemAppointmentDateBinding
import com.ents_h108.petwell.view.main.featureAppointment.DokterProfileAppointmentFragmentDirections

class AppointmentDateAdapter :
    ListAdapter<WorkSchedule, AppointmentDateAdapter.AppointmentDateViewHolder>(DIFF_CALLBACK) {

    interface OnItemClickListener {
        fun onItemClick(item: WorkHours)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentDateViewHolder {
        val binding = ItemAppointmentDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AppointmentDateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AppointmentDateViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AppointmentDateViewHolder(private val binding: ItemAppointmentDateBinding) :
        RecyclerView.ViewHolder(binding.root), AppointmentHourAdapter.OnItemClickListener {

        private lateinit var hourAdapter: AppointmentHourAdapter

        fun bind(item: WorkSchedule) {
            binding.apply {
                tvDate.text = item.date
                hourAdapter = AppointmentHourAdapter(this@AppointmentDateViewHolder)
                rvHour.layoutManager = LinearLayoutManager(rvHour.context, LinearLayoutManager.HORIZONTAL, false)
                rvHour.adapter = hourAdapter
                hourAdapter.submitList(item.workHours)
            }
        }

        override fun onItemClick(item: WorkHours) {
            itemView.findNavController().navigate(DokterProfileAppointmentFragmentDirections.actionDokterProfileAppointmentFragmentToInvoiceAppointmentFragment(item))
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
