package com.example.naregua

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.naregua.databinding.ActivityTelaDeCadastroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class CadastroActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;
    val database = Firebase.database
    private val binding by lazy{ ActivityTelaDeCadastroBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth
        binding.ibVoltar.setOnClickListener {
            finish()
        }

        binding.button2.setOnClickListener {
            binding.pbLoading.isVisible = true
            if(verificaDados()){
                auth.createUserWithEmailAndPassword(binding.editEmail.text.toString(), binding.editTextTextPassword.text.toString())
                    .addOnCompleteListener (this){task ->
                        if(task.isSuccessful){
                            val user = auth.currentUser
                            val uid = user?.uid
                            val userRef = database.getReference("users").child(uid ?: "")

                            val userData = hashMapOf(
                                "nome" to binding.editTextText.text.toString(),
                                "sobrenome" to binding.textView3.text.toString(),
                                "idade" to binding.editTextNumber.text.toString(),
                                "cpf" to binding.editTextNumber2.text.toString(),
                                "cep" to binding.editTextNumber3.text.toString(),
                                "logradouro" to binding.editTextNumber4.text.toString(),
                                "numero" to binding.editTextNumber5.text.toString()
                            )

                            userRef.setValue(userData).addOnSuccessListener {
                                user?.sendEmailVerification()
                                    ?.addOnCompleteListener { emailVerificationTask ->
                                        if (emailVerificationTask.isSuccessful) {
                                            Log.d(
                                                "TAG",
                                                "E-mail de verificação enviado com sucesso"
                                            )

                                            val intent = Intent(this, LoginActivity::class.java)
                                            startActivity(intent)
                                            finish()
                                        } else {
                                            Log.e(
                                                "TAG",
                                                "Erro ao enviar e-mail de verificação: ${emailVerificationTask.exception?.message}"
                                            )
                                        }
                                    }
                            }.addOnFailureListener {
                                    Log.e("TAG", "Erro ao salvar dados do usuário: ${it.message}")
                            }

                        } else{
                            val exception = task.exception
                            exception?.let{
                                Log.e("TAG", "Erro ao criar usuário: ${it.message}")
                            }
                        }

                    }
            }else{
                Toast.makeText(this, "Ainda faltam campos a serem preenchidos", Toast.LENGTH_LONG).show()
            }
        }

        binding.ibVoltar.setOnClickListener {
            finish()
        }
    }

    fun verificaDados(): Boolean{
        return !(binding.editEmail.text.toString().isEmpty() ||
                binding.editTextText.text.toString().isEmpty() ||
                binding.textView3.text.toString().isEmpty() ||
                binding.editTextNumber.text.toString().isEmpty() ||
                binding.editTextNumber2.text.toString().isEmpty() ||
                binding.editTextNumber3.text.toString().isEmpty() ||
                binding.editTextNumber4.text.toString().isEmpty() ||
                binding.editTextNumber5.text.toString().isEmpty() ||
                binding.editTextTextPassword.text.toString().isEmpty())
    }
}