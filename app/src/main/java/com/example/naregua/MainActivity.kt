package com.example.naregua

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.naregua.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
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
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
    }
}