package btu.ninidze.stepcounter.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import btu.ninidze.stepcounter.data.network.models.collection.CollectionItem
import btu.ninidze.stepcounter.databinding.ItemSneakersBinding
import btu.ninidze.stepcounter.util.extensions.loadImg

typealias OnSneakerClick = (String, String) -> Unit
class CollectionRecyclerViewAdapter(private val from: String, private val onItemClick: OnSneakerClick): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val currentList = mutableListOf<CollectionItem>()

    inner class SneakersViewHolder(private val binding: ItemSneakersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: CollectionItem) = with(binding) {
            tvName.text = data.name
            imageView.loadImg(data.imageUrl)
            btnBuy.text = data.price.toString()
            root.setOnClickListener {
                onItemClick.invoke(data.tokenId, from)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SneakersViewHolder(ItemSneakersBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SneakersViewHolder).onBind(
            currentList[position]
        )
    }

    override fun getItemCount() = currentList.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<CollectionItem>) {
        this.currentList.clear()
        this.currentList.addAll(newList)
        notifyDataSetChanged()
    }
}