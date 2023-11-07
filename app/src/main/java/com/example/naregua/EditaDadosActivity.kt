package com.example.naregua

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.example.naregua.databinding.ActivityEditaDadosBinding
import com.example.naregua.databinding.ActivityTelaDeCadastroBinding

class EditaDadosActivity : AppCompatActivity() {
    private val binding by lazy{ActivityEditaDadosBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.ibVoltar.setOnClickListener {
            finish()
        }
    }
}