package com.example.naregua.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.naregua.CabeleireiroItemUser
import com.example.naregua.databinding.CabeleireiroItemBinding

class CabeleireirosAdapter : ListAdapter<CabeleireiroItemUser, CabeleireirosAdapter.ViewHolder>(DiffCallbackCab()) {

    var click: (CabeleireiroItemUser) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CabeleireiroItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: CabeleireiroItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CabeleireiroItemUser) {
            binding.ivFachada.setImageBitmap(item.imagemId)
            binding.root.setOnClickListener{
                click(item)
            }
        }
    }
}

class DiffCallbackCab : DiffUtil.ItemCallback<CabeleireiroItemUser>() {
    override fun areItemsTheSame(oldItem: CabeleireiroItemUser, newItem: CabeleireiroItemUser) = oldItem == newItem
    override fun areContentsTheSame(oldItem: CabeleireiroItemUser, newItem: CabeleireiroItemUser) =
        oldItem.id == newItem.id
}