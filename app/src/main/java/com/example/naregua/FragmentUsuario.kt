package com.example.naregua

import android.content.Intent
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

class FragmentUsuario: Fragment() {
    private lateinit var binding: FragmentUsuarioBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentUsuarioBinding.inflate(inflater, container, false)

        binding.btnAjuda.setOnClickListener {
            DialogAjuda.show(
                title = "Informações de contato",
                fragmentManager = parentFragmentManager
            )
        }

        binding.btnDeletacad.setOnClickListener {
            DialogCadDeletado.show(
                title = "Seu Cadastro será deletado",
                fragmentManager = parentFragmentManager
            )
        }

        binding.btnSairapp.setOnClickListener {
            requireActivity().finishAffinity()
        }
        binding.btnEditardados.setOnClickListener {
            val intent = Intent(requireContext(), EditaDadosActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }
}