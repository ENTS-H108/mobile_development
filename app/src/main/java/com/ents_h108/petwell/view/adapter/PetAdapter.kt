import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ents_h108.petwell.databinding.ItemPetBinding

data class PetItem(val imageUrl: String, val petName: String, val petRace: String)

class PetAdapter(private val listener: OnItemClickListener) :
    ListAdapter<PetItem, PetAdapter.PetViewHolder>(DIFF_CALLBACK) {

    interface OnItemClickListener {
        fun onItemClick(item: PetItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val binding = ItemPetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PetViewHolder(private val binding: ItemPetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val petItem = getItem(position)
                    listener.onItemClick(petItem)
                }
            }
        }

        fun bind(item: PetItem) {
            binding.imgPet.load(item.imageUrl)
            binding.tvPetName.text = item.petName
            binding.tvRace.text = item.petRace
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
