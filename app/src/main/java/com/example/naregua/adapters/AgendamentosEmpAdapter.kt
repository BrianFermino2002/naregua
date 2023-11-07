package com.example.naregua.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.naregua.AgendamentoEmpresa
import com.example.naregua.AgendamentoUser
import com.example.naregua.databinding.AgendamentoEmpItemBinding

class AgendamentosEmpAdapter : ListAdapter<AgendamentoEmpresa, AgendamentosEmpAdapter.ViewHolder>(DiffCallbackEmpr()) {

    var click: (AgendamentoEmpresa) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AgendamentoEmpItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: AgendamentoEmpItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AgendamentoEmpresa) {
            binding.tvAgendamento.text = "Agendamento: " + item.nomeUser

            binding.ibExcluir.setOnClickListener {

            }
        }
    }
}

class DiffCallbackEmpr : DiffUtil.ItemCallback<AgendamentoEmpresa>() {
    override fun areItemsTheSame(oldItem: AgendamentoEmpresa, newItem: AgendamentoEmpresa) = oldItem == newItem
    override fun areContentsTheSame(oldItem: AgendamentoEmpresa, newItem: AgendamentoEmpresa) =
        oldItem.nomeUser == newItem.nomeUser
}