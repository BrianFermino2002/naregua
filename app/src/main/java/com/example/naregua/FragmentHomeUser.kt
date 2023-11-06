package com.example.naregua

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.naregua.adapters.CabeleireirosAdapter
import com.example.naregua.databinding.FragmentHomeUserBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

class FragmentHomeUser : Fragment() {
    private var listaDeEmpresas: MutableList<CabeleireiroItemUser> = mutableListOf()
    private lateinit var binding: FragmentHomeUserBinding
    private val adapter by lazy { CabeleireirosAdapter() }
    private var isListLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeUserBinding.inflate(inflater, container, false)
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
        listaDeEmpresas.clear()
        val usuariosRef = Firebase.database.reference.child("empresas")
        val storage = Firebase.storage
        val storageRef = storage.reference

        usuariosRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val totalItens = snapshot.childrenCount.toInt()
                var itensCarregados = 0

                for (usuarioSnapshot in snapshot.children) {
                    val id = usuarioSnapshot.key.toString()
                    val imageRef = storageRef.child("fachadas/$id.jpg")

                    val localFile = File.createTempFile("tempImage", "jpg")
                    imageRef.getFile(localFile).addOnSuccessListener { uri ->
                        val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                        listaDeEmpresas.add(CabeleireiroItemUser(id, bitmap))

                        itensCarregados++

                        if (itensCarregados == totalItens) {
                            adapter.submitList(listaDeEmpresas)
                            binding.pbLoading.isVisible = false
                            isListLoaded = true
                        }
                    }.addOnFailureListener { exception ->
                        Log.e("TAG", "Erro ao obter URL da imagem: ${exception.message}")

                        itensCarregados++

                        if (itensCarregados == totalItens) {
                            adapter.submitList(listaDeEmpresas)
                            binding.pbLoading.isVisible = false
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TAG", "Erro ao obter dados do Firebase: ${error.message}")
            }
        })
    }

    private fun setupAdapter() {
        binding.rvCabeleireiros.adapter = adapter

        adapter.click = {
            val intent = Intent(requireContext(), EstabelecimentoActivity::class.java)
            intent.putExtra("id", it.id)
            startActivity(intent)
        }
    }
}
