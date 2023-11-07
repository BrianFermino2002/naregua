package com.example.naregua

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.naregua.databinding.ActivityMainBinding
import com.example.naregua.dialogs.DialogEscolhaCadastrar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    private val binding by lazy{ ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val user = FirebaseAuth.getInstance().currentUser
        val idUser = user?.uid

        if(idUser != null){
            FirebaseAuth.getInstance().signOut()
        }
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