package com.example.naregua

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.naregua.databinding.ActivityConfirmacaoAgendamentoBinding
import com.example.naregua.databinding.ActivityEstabelecimentoBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

class ConfirmacaoAgendamentoActivity : AppCompatActivity() {
    private lateinit var agendamento: Agendamento
    private val binding by lazy{ ActivityConfirmacaoAgendamentoBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        agendamento = intent.getParcelableExtra("agend")!!

        val servicos = "Serviço(s):\n" + agendamento.servicos.joinToString("\n")
        binding.tvServ.text = servicos

        binding.tvDataagend.text = "Data do Agendamento: " + agendamento.dia + "/" + agendamento.mes + "/" + agendamento.ano +
                " ás " + agendamento.horario

        binding.tvValor.text = "Valor total: " + agendamento.valor.toString()
        loadInfos()
    }

    private fun loadInfos(){
        val usuariosRef = Firebase.database.reference.child("empresas").child(agendamento.idEmpresa)


        val storage = Firebase.storage
        val storageRef = storage.reference
        val imageRef = storageRef.child("fachadas/${agendamento.idEmpresa}.jpg")

        val localFile = File.createTempFile("tempImage", "jpg")
        imageRef.getFile(localFile).addOnSuccessListener { uri ->
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            binding.ivFachada.setImageBitmap(bitmap)
        }
        usuariosRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val nomeEmp = snapshot.child("nomeEmp").value.toString()
                    binding.tvNomeEmp.text = nomeEmp
                } else {

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TAG", "Erro ao obter dados do Firebase: ${error.message}")
            }
        })

    }
}