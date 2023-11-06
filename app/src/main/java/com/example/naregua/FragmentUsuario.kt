package com.example.naregua

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.naregua.databinding.FragmentSearchBinding
import com.example.naregua.databinding.FragmentUsuarioBinding

class FragmentUsuario: Fragment() {
    private lateinit var binding: FragmentUsuarioBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentUsuarioBinding.inflate(inflater, container, false)
        return binding.root
    }
}