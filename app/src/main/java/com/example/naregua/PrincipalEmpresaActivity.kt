package com.example.naregua

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.naregua.databinding.ActivityCadastroEmpresaBinding
import com.example.naregua.databinding.ActivityPrincipalEmpresaBinding
import com.example.naregua.databinding.ActivityPrincipalUserBinding

class PrincipalEmpresaActivity : AppCompatActivity() {
    private val binding by lazy{ ActivityPrincipalEmpresaBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment2)
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)
    }
}