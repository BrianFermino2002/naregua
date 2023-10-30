package com.example.naregua

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.naregua.databinding.ActivityMainBinding
import com.example.naregua.dialogs.DialogEscolhaCadastrar
import com.google.firebase.FirebaseApp


class MainActivity : AppCompatActivity() {
    private val binding by lazy{ ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSoucadastrado.setOnClickListener {
            val intent =  Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnSemcadastro.setOnClickListener {
            val intent = Intent(this, PrincipalUserActivity::class.java)
            startActivity(intent)
        }

        binding.btnCadastrar.setOnClickListener {
            DialogEscolhaCadastrar.show(
                title = "Deseja Cadastrar:",
                fragmentManager = supportFragmentManager
            )
        }

    }
}