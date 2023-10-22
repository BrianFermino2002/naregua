package com.example.naregua

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.naregua.databinding.FragmentAgendamentosBinding

class FragmentAgendamentos: Fragment() {
    private lateinit var binding: FragmentAgendamentosBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        return binding.root
    }
}