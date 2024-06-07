import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.ItemPetBinding

data class PetItem(val imageUrl: String, val petName: String, val petRace: String)
class PetAdapter(private val listener: OnItemClickListener) :
    ListAdapter<PetItem, PetAdapter.PetViewHolder>(DIFF_CALLBACK) {

    private var selectedPosition = RecyclerView.NO_POSITION

    interface OnItemClickListener {
        fun onItemClick(item: PetItem)
        fun onEditProfileClick(item: PetItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val binding = ItemPetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        holder.bind(getItem(position), position == selectedPosition)
    }

    inner class PetViewHolder(private val binding: ItemPetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    selectedPosition = position
                    notifyDataSetChanged()
                    val petItem = getItem(position)
                    listener.onItemClick(petItem)
                }
            }

            binding.tvEditPet.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val petItem = getItem(position)
                    listener.onEditProfileClick(petItem)
                }
            }
        }

        fun bind(item: PetItem, isSelected: Boolean) {
            binding.imgPet.load(item.imageUrl)
            binding.tvPetName.text = item.petName
            binding.tvRace.text = item.petRace
            if (isSelected) {
                binding.cardPet.setBackgroundResource(R.drawable.card_pet_active)
                binding.tvStatus.text = itemView.context.getString(R.string.active)
            } else {
                binding.cardPet.setBackgroundResource(android.R.color.white)
                binding.tvStatus.text = ""
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PetItem>() {
            override fun areItemsTheSame(oldItem: PetItem, newItem: PetItem): Boolean {
                return oldItem.imageUrl == newItem.imageUrl
            }

            override fun areContentsTheSame(oldItem: PetItem, newItem: PetItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}