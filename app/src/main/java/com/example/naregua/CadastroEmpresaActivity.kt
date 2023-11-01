package com.example.naregua

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.view.isVisible
import com.example.naregua.databinding.ActivityCadastroEmpresaBinding
import com.example.naregua.databinding.ActivityPrincipalUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CadastroEmpresaActivity : AppCompatActivity() {
    companion object {
        const val PICK_IMAGE_REQUEST = 1
    }

    private lateinit var auth: FirebaseAuth;
    val database = Firebase.database
    private val binding by lazy{ ActivityCadastroEmpresaBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.ibVoltar.setOnClickListener {
            finish()
        }

        binding.ibSelecionefachada.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            binding.ivSelectimage.isVisible = true
            val selectedImageUri = data.data
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