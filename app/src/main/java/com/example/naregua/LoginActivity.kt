package com.example.naregua

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.naregua.databinding.ActivityTelaDeCadastroBinding
import com.example.naregua.databinding.ActivityTelaDeLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;
    private val binding by lazy{ ActivityTelaDeLoginBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnEntrar.setOnClickListener {
            if(verificaDados()){
                auth.signInWithEmailAndPassword(binding.etEmail.text.toString(), binding.etPassword.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, PrincipalUserActivity::class.java)
                            startActivity(intent)

                        } else {
                            // O login falhou
                            val exception = task.exception
                            Toast.makeText(this, "Dados incorretos", Toast.LENGTH_LONG).show()
                        }
                    }
            } else{
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show()
            }

        }

        binding.ibVoltar.setOnClickListener {
            finish()
        }
    }

    fun verificaDados(): Boolean{
        return !(binding.etEmail.text.toString().isEmpty() || binding.etPassword.text.toString().isEmpty())
    }
}