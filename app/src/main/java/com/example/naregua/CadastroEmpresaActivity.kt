package com.example.naregua

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import com.example.naregua.databinding.ActivityCadastroEmpresaBinding
import com.example.naregua.databinding.ActivityPrincipalUserBinding
import com.example.naregua.dialogs.DialogEscolhaCadastrar
import com.example.naregua.dialogs.DialogInserirServicos
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class CadastroEmpresaActivity : AppCompatActivity() {
    companion object {
        const val PICK_IMAGE_REQUEST = 1
    }

    private var listaDeServicos: MutableList<Servico> = mutableListOf()
    private lateinit var selectedImageUri: Uri
    private lateinit var auth: FirebaseAuth;
    val database = Firebase.database
    private val binding by lazy{ ActivityCadastroEmpresaBinding.inflate(layoutInflater)}
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        listaDeServicos = mutableListOf()
        auth = Firebase.auth

        binding.ibVoltar.setOnClickListener {
            finish()
        }

        binding.ibSelecionefachada.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        supportFragmentManager.setFragmentResultListener(DialogInserirServicos.FRAGMENT_RESULT, this) { requestKey, bundle ->
            val nome = bundle.getString(DialogInserirServicos.NOME_SERVICO)?:""
            val valor = bundle.getString(DialogInserirServicos.VALOR_SERVICO)?:""

            val servico = Servico(nome, valor.toDouble())
            listaDeServicos.add(servico)

            val novoServicoView = layoutInflater.inflate(R.layout.servicoscad_item, null)

            novoServicoView.findViewById<TextView>(R.id.tv_descricao).text = nome
            novoServicoView.findViewById<TextView>(R.id.tv_valor).text = "R$ $valor"

            binding.llServicosContainer.addView(novoServicoView)

        }
        binding.btnAdicionaServ.setOnClickListener {
            DialogInserirServicos.show(
                title = "Cadastrar serviço",
                fragmentManager = supportFragmentManager
            )

        }

        binding.button2.setOnClickListener {
            if(verificaDados()){
                auth.createUserWithEmailAndPassword(binding.etEmail.text.toString(), binding.etSenha.text.toString())
                    .addOnCompleteListener (this){task ->
                        if(task.isSuccessful){
                            val user = auth.currentUser
                            val uid = user?.uid
                            val userRef = database.getReference("empresas").child(uid ?: "")

                            val userData = hashMapOf(
                                "nomeEmp" to binding.etNomeEmp.text.toString(),
                                "nomeProp" to binding.etNomeProp.text.toString(),
                                "cnpj" to binding.etCnpj.text.toString(),
                                "cep" to binding.etCep.text.toString(),
                                "logradouro" to binding.etLogradouro.text.toString(),
                                "numero" to binding.etNumero.text.toString(),
                                "agencia" to binding.etAgencia.text.toString(),
                                "conta" to binding.etConta.text.toString(),
                                "servicos" to listaDeServicos
                            )

                            userRef.setValue(userData).addOnSuccessListener {
                                val storage = Firebase.storage
                                val storageRef = storage.reference
                                val imageRef = storageRef.child("fachadas/$uid.jpg")

                                val image = selectedImageUri
                                    image.let {
                                        imageRef.putFile(it)
                                            .addOnSuccessListener { taskSnapshot ->
                                                // Imagem carregada com sucesso
                                                Log.d("TAG", "Imagem carregada com sucesso")
                                            }
                                            .addOnFailureListener { exception ->
                                                // Erro ao carregar a imagem
                                                Log.e("TAG", "Erro ao carregar imagem: ${exception.message}")
                                            }
                                    }
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
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            binding.ivSelectimage.isVisible = true
            selectedImageUri = data.data!!
            binding.ivSelectimage.setImageURI(selectedImageUri)
        }
    }

    fun verificaDados(): Boolean{
        return !(binding.etNomeEmp.text.toString().isEmpty() ||
                binding.etNomeProp.text.toString().isEmpty() ||
                binding.etCnpj.text.toString().isEmpty() ||
                binding.etCep.text.toString().isEmpty() ||
                binding.etLogradouro.text.toString().isEmpty() ||
                binding.etNumero.text.toString().isEmpty() ||
                binding.etEmail.text.toString().isEmpty() ||
                binding.etSenha.text.toString().isEmpty() ||
                binding.etAgencia.text.toString().isEmpty() ||
                binding.etConta.text.toString().isEmpty())
    }
}