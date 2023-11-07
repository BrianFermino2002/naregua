package com.example.naregua

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.naregua.adapters.AgendamentosUserAdapter
import com.example.naregua.adapters.CabeleireirosAdapter
import com.example.naregua.databinding.FragmentAgendamentosBinding
import com.example.naregua.databinding.FragmentSearchBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

class FragmentAgendamentos: Fragment() {
    private lateinit var binding: FragmentAgendamentosBinding
    private val adapter by lazy { AgendamentosUserAdapter() }
    private var listaDeAgendamentos: MutableList<AgendamentoUser> = mutableListOf()
    private var isListLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentAgendamentosBinding.inflate(inflater, container, false)
        setupAdapter()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (!isListLoaded) { // Verifica se a lista já foi carregada
            carregarList()
        }
    }

    private fun carregarList() {
        binding.pbLoading.isVisible = true
        listaDeAgendamentos.clear()
        val user = FirebaseAuth.getInstance().currentUser
        val idUser = user?.uid
        if(idUser != null){
            val usuariosRef = Firebase.database.reference.child("users").child(idUser).child("agendamentos")

            usuariosRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val totalItens = snapshot.childrenCount.toInt()
                    var itensCarregados = 0

                    for (usuarioSnapshot in snapshot.children) {
                        val idEmpresa = usuarioSnapshot.child("idEmpresa").value.toString()
                        val dia = usuarioSnapshot.child("dia").value.toString().toInt()
                        val mes = usuarioSnapshot.child("mes").value.toString().toInt()
                        val ano = usuarioSnapshot.child("ano").value.toString().toInt()
                        val horario = usuarioSnapshot.child("horario").value.toString()
                        val valor = usuarioSnapshot.child("valor").value.toString().toDouble()
                        val nomeEmp = Firebase.database.reference.child("empresas").child(idEmpresa)

                        nomeEmp.addListenerForSingleValueEvent(object : ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.exists()) {
                                    val empresa = snapshot.child("nomeEmp").value.toString()

                                    listaDeAgendamentos.add(AgendamentoUser(empresa, dia, mes, ano, horario, valor))

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
        } else{
            Toast.makeText(requireContext(), "Para ver esse conteúdo, faça login", Toast.LENGTH_LONG).show()
        }

    }
    private fun setupAdapter() {
        binding.rvCabeleireiros.adapter = adapter

    }
}