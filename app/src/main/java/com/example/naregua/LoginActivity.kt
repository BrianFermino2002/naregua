package com.example.naregua

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.naregua.databinding.ActivityTelaDeCadastroBinding
import com.example.naregua.databinding.ActivityTelaDeLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
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
                            val user = auth.currentUser
                            val userId = user?.uid

                            val usuariosRef = Firebase.database.reference.child("users")
                            val empresasRef = Firebase.database.reference.child("empresas")

                            usuariosRef.child(userId!!).addListenerForSingleValueEvent(object :
                                ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if (snapshot.exists()) {
                                        val intent = Intent(applicationContext, PrincipalUserActivity::class.java)
                                        startActivity(intent)
                                    } else {
                                        // É uma empresa
                                        val intent = Intent(applicationContext, PrincipalEmpresaActivity::class.java)
                                        startActivity(intent)
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    // Handle error
                                }
                            })
                        } else {
                            // O login falhou
                            val exception = task.exception
                            Toast.makeText(this, "Dados incorretos", Toast.LENGTH_LONG).show()
                        }
                    }

        }

        binding.ibVoltar.setOnClickListener {
            finish()
        }
    }


}
    fun verificaDados(): Boolean{
        return !(binding.etEmail.text.toString().isEmpty() || binding.etPassword.text.toString().isEmpty())
    }
}