package com.example.naregua

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.naregua.databinding.ActivityTelaDeCadastroBinding

class CadastroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTelaDeCadastroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.ibVoltar.setOnClickListener {
            finish()
        }
        binding.button2.setOnClickListener {
            if(verificaDados()){

            }else{
                Toast.makeText(this, "Ainda faltam campos a serem preenchidos", Toast.LENGTH_LONG).show()
            }
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