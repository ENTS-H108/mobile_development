import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ents_h108.petwell.R
import com.ents_h108.petwell.databinding.ItemHistoryBinding

data class HistoryItem(
    val type: Int,
    val imageUrl: String,
    val title: String,
    val deskripsi: String,
    val tanggal: String
)

class HistoryAdapter(private val listener: OnItemClickListener) :
    ListAdapter<HistoryItem, HistoryAdapter.HistoryViewHolder>(DIFF_CALLBACK) {

    interface OnItemClickListener {
        fun onItemClick(item: HistoryItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding =
            ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class HistoryViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val historyItem = getItem(position)
                    listener.onItemClick(historyItem)
                }
            }
        }

        fun bind(item: HistoryItem) {
            binding.apply {
                when (item.type) {
                    1 -> tvHistoryType.text =
                        root.context.getString(R.string.consultation_title)
                    2 -> tvHistoryType.text =
                        root.context.getString(R.string.appointment_title)
                    else -> tvHistoryType.text = root.context.getString(R.string.scan_title)
                }
                imgHistory.load(item.imageUrl)
                tvTitle.text = item.title
                tvDeskripsi.text = item.deskripsi
                tvTanggal.text = item.tanggal
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoryItem>() {
            override fun areItemsTheSame(oldItem: HistoryItem, newItem: HistoryItem): Boolean {
                return oldItem.imageUrl == newItem.imageUrl
            }

            override fun areContentsTheSame(oldItem: HistoryItem, newItem: HistoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
