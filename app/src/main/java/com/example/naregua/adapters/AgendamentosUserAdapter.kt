package com.example.naregua.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.naregua.AgendamentoUser
import com.example.naregua.CabeleireiroItemUser
import com.example.naregua.databinding.AgendamentoItemBinding
import com.example.naregua.databinding.CabeleireiroItemBinding

class AgendamentosUserAdapter : ListAdapter<AgendamentoUser, AgendamentosUserAdapter.ViewHolder>(DiffCallbackAgend()) {

    var click: (AgendamentoUser) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AgendamentoItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: AgendamentoItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AgendamentoUser) {
            binding.tvNomeEmp.text = item.nomeEmp
            binding.tvData.text = item.dia.toString() + "/" +
                    item.mes.toString() + "/" + item.ano.toString() +
                    " ás " + item.Horario
            binding.tvValor.text = "R$" + item.valor.toString()
        }
    }
}

class DiffCallbackAgend : DiffUtil.ItemCallback<AgendamentoUser>() {
    override fun areItemsTheSame(oldItem: AgendamentoUser, newItem: AgendamentoUser) = oldItem == newItem
    override fun areContentsTheSame(oldItem: AgendamentoUser, newItem: AgendamentoUser) =
        oldItem.nomeEmp == newItem.nomeEmp
}