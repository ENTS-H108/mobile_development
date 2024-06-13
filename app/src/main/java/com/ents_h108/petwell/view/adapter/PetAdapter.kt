package com.ents_h108.petwell.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ents_h108.petwell.R
import com.ents_h108.petwell.data.model.Pet
import com.ents_h108.petwell.databinding.ItemPetBinding

class PetAdapter(private val listener: OnItemClickListener, private var selectedPetId: String?) :
    ListAdapter<Pet, PetAdapter.PetViewHolder>(DIFF_CALLBACK) {

    private var previousSelectedPosition: Int = RecyclerView.NO_POSITION

    interface OnItemClickListener {
        fun onItemClick(item: Pet)
        fun onEditProfileClick(item: Pet)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val binding = ItemPetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        holder.bind(getItem(position), getItem(position).id == selectedPetId)
        if (getItem(position).id == selectedPetId) {
            previousSelectedPosition = holder.bindingAdapterPosition
        }
    }

    inner class PetViewHolder(private val binding: ItemPetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val petItem = getItem(position)
                    selectedPetId = petItem.id
                    if (previousSelectedPosition != RecyclerView.NO_POSITION) {
                        notifyItemChanged(previousSelectedPosition)
                    }
                    notifyItemChanged(position)
                    previousSelectedPosition = position
                    listener.onItemClick(petItem)
                }
            }

            binding.tvEditPet.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val petItem = getItem(position)
                    listener.onEditProfileClick(petItem)
                }
            }
        }

        fun bind(item: Pet, isSelected: Boolean) {
            binding.apply {
                tvPetName.text = item.name
                tvRace.text = item.species
                imgPet.load(if (item.species == "anjing") R.drawable.avatar_dog else R.drawable.avatar_cat)
                cardPet.setBackgroundResource(if (isSelected) R.drawable.card_pet_active else android.R.color.white)
                tvStatus.text = if (isSelected) itemView.context.getString(R.string.active) else ""
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Pet>() {
            override fun areItemsTheSame(oldItem: Pet, newItem: Pet): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Pet, newItem: Pet): Boolean {
                return oldItem == newItem
            }
        }
    }
}
