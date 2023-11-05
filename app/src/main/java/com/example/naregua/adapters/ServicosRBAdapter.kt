package com.example.naregua.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.naregua.Servico
import com.example.naregua.databinding.ServicosItemBinding

class ServicosRBAdapter : ListAdapter<Servico, ServicosRBAdapter.ViewHolder>(DiffCallback()) {
    val selectedItems = mutableSetOf<Servico>()
    var click: (Servico) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ServicosItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ServicosItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Servico) {

            binding.cbServ.text = item.descricao
            binding.tvValor.text = item.valor.toString()
            binding.cbServ.isChecked = selectedItems.contains(item)

            binding.cbServ.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked){
                    selectedItems.add(item)
                }else {
                    selectedItems.remove(item)
                }
            }
        }
    }

}

class DiffCallback : DiffUtil.ItemCallback<Servico>() {
    override fun areItemsTheSame(oldItem: Servico, newItem: Servico) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Servico, newItem: Servico) =
        oldItem.descricao == newItem.descricao
}