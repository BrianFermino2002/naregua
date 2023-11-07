package com.example.naregua

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.naregua.databinding.FragmentEmpresaBinding
import com.example.naregua.databinding.FragmentHomeEmpresaBinding
import com.example.naregua.databinding.FragmentSearchBinding
import com.example.naregua.dialogs.DialogAjuda
import com.example.naregua.dialogs.DialogCadDeletado

class FragmentEmpresa: Fragment() {
    private lateinit var binding: FragmentEmpresaBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentEmpresaBinding.inflate(inflater, container, false)

        binding.btnAjuda.setOnClickListener {
            DialogAjuda.show(
                title = "Informações de contato",
                fragmentManager = parentFragmentManager
            )
        }

        binding.btnDeletacad.setOnClickListener {
            DialogCadDeletado.show(
                title = "Sua empresa será deletada",
                fragmentManager = parentFragmentManager
            )
        }

        binding.btnSairapp.setOnClickListener {
            requireActivity().finishAffinity()
        }
        binding.btnEditardados.setOnClickListener {
            val intent = Intent(requireContext(), CadastroEmpresaActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}