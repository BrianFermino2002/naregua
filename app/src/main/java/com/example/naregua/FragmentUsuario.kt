package com.example.naregua

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.naregua.databinding.FragmentSearchBinding
import com.example.naregua.databinding.FragmentUsuarioBinding
import com.example.naregua.dialogs.DialogAjuda
import com.example.naregua.dialogs.DialogCadDeletado
import com.example.naregua.dialogs.DialogInserirServicos
import com.google.firebase.auth.FirebaseAuth

class FragmentUsuario: Fragment() {
    private lateinit var binding: FragmentUsuarioBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentUsuarioBinding.inflate(inflater, container, false)
        val user = FirebaseAuth.getInstance().currentUser

        val idUser = user?.uid
        if(idUser != null){

            binding.btnDeletacad.setTextColor(Color.WHITE)
            binding.btnDeletacad.setBackgroundColor(Color.RED)
            binding.btnDeletacad.isClickable = true
            binding.btnEditardados.isClickable = true
            binding.btnEditardados.setBackgroundColor(Color.parseColor("#05DBF2"))
            binding.btnDeletacad.setOnClickListener {
                DialogCadDeletado.show(
                    title = "Seu Cadastro será deletado",
                    fragmentManager = parentFragmentManager
                )
            }

            binding.btnEditardados.setOnClickListener {
                val intent = Intent(requireContext(), EditaDadosActivity::class.java)
                startActivity(intent)
            }

        }else{
            binding.btnDeletacad.setTextColor(Color.BLACK)
            binding.btnDeletacad.setBackgroundColor(Color.GRAY)
            binding.btnDeletacad.isClickable = false
            binding.btnEditardados.isClickable = false
            binding.btnEditardados.setBackgroundColor(Color.GRAY)
        }

        binding.btnAjuda.setOnClickListener {
            DialogAjuda.show(
                title = "Informações de contato",
                fragmentManager = parentFragmentManager
            )
        }

        binding.btnSairapp.setOnClickListener {
            requireActivity().finishAffinity()
        }

        return binding.root
    }
}