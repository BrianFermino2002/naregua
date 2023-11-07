package com.example.naregua

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.naregua.adapters.AgendamentosEmpAdapter
import com.example.naregua.adapters.AgendamentosUserAdapter
import com.example.naregua.databinding.FragmentHomeEmpresaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FragmentHomeEmp: Fragment() {
    private lateinit var binding: FragmentHomeEmpresaBinding
    private val adapter by lazy { AgendamentosEmpAdapter() }
    private var listaDeAgendamentos: MutableList<AgendamentoEmpresa> = mutableListOf()
    private var isListLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentHomeEmpresaBinding.inflate(inflater, container, false)
        setupAdapter()
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        carregarList()
    }
    private fun carregarList() {
        binding.pbLoading.isVisible = true
        listaDeAgendamentos.clear()
        val user = FirebaseAuth.getInstance().currentUser
        val idUser = user?.uid!!
        val usuariosRef = Firebase.database.reference.child("empresas").child(idUser).child("agendamentos")

        usuariosRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val totalItens = snapshot.childrenCount.toInt()
                var itensCarregados = 0

                for (usuarioSnapshot in snapshot.children) {
                    val idUsuario = usuarioSnapshot.child("idUser").value.toString()
                    val dia = usuarioSnapshot.child("dia").value.toString().toInt()
                    val mes = usuarioSnapshot.child("mes").value.toString().toInt()
                    val ano = usuarioSnapshot.child("ano").value.toString().toInt()
                    val horario = usuarioSnapshot.child("horario").value.toString()
                    val valor = usuarioSnapshot.child("valor").value.toString().toDouble()
                    val nomeUser = Firebase.database.reference.child("users").child(idUsuario)
                    val idAgendamento = usuarioSnapshot.key!!

                    nomeUser.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                val usuario = snapshot.child("nome").value.toString()

                                listaDeAgendamentos.add(AgendamentoEmpresa(idAgendamento, usuario, dia, mes, ano, horario, valor))

                                itensCarregados++

                                if (itensCarregados == totalItens) {
                                    adapter.submitList(listaDeAgendamentos)
                                    binding.pbLoading.isVisible = false
                                    isListLoaded = true
                                }
                            } else {

                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.e("TAG", "Erro ao obter dados do Firebase: ${error.message}")
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TAG", "Erro ao obter dados do Firebase: ${error.message}")
            }
        })
    }
    private fun setupAdapter() {
        binding.rvAgendamentosemp.adapter = adapter

    }
}