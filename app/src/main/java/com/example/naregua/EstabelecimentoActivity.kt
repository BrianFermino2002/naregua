package com.example.naregua

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.naregua.adapters.ServicosRBAdapter
import com.example.naregua.databinding.ActivityEstabelecimentoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.time.Duration.Companion.days

class EstabelecimentoActivity : AppCompatActivity() {
    private var idUser: String? = null
    private val adapter by lazy { ServicosRBAdapter() }
    private var listaDeEmpresas: MutableList<Servico> = mutableListOf()
    private var valorTotal: Double = 0.0
    private val binding by lazy{ ActivityEstabelecimentoBinding.inflate(layoutInflater)}
    private lateinit var id: String
    var dia = 0
    var mes = 0
    var ano = 0
    var horario = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val user = FirebaseAuth.getInstance().currentUser
        idUser = user?.uid

        id = intent.getStringExtra("id")!!
        ajeitaCalendario()
        ajeitaSpinner()
        setupAdapter()

        loadInfos()
        binding.ibVoltar.setOnClickListener{
            finish()
        }

        binding.cvCalendario.setOnDateChangeListener { view, year, month, dayOfMonth ->
            dia = dayOfMonth
            mes = month
            ano = year
        }

        binding.spnHorarios.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                horario = parent?.getItemAtPosition(position).toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        binding.btnAgendar.setOnClickListener {
            if(idUser != null){
                val listaServicos = obterItensSelecionados()
                valorTotal = obterValorTotal()

                val intent = Intent(this, ConfirmacaoAgendamentoActivity::class.java)
                intent.putExtra("agend", Agendamento(idUser!!, id, valorTotal, listaServicos, dia, mes, ano, horario))
                startActivity(intent)
            } else{
                Toast.makeText(this, "Faça login para ver o restante do aplicativo", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun loadInfos(){
        val usuariosRef = Firebase.database.reference.child("empresas").child(id)

        val storage = Firebase.storage
        val storageRef = storage.reference
        val imageRef = storageRef.child("fachadas/$id.jpg")

        val localFile = File.createTempFile("tempImage", "jpg")
        imageRef.getFile(localFile).addOnSuccessListener { uri ->
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            binding.ivFachada.setImageBitmap(bitmap)
            }
        usuariosRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val nomeProp = snapshot.child("nomeProp").value.toString()
                    val endereco = snapshot.child("logradouro").value.toString() + ", " + snapshot.child("numero").value.toString()
                    binding.tvNomeempresa.text = snapshot.child("nomeEmp").value.toString()
                    binding.tvDono.text = "Nome do Proprietário: " + nomeProp
                    binding.tvEndereco.text = "Endereço: " + endereco
                } else {

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TAG", "Erro ao obter dados do Firebase: ${error.message}")
            }
        })

        val servicosRef = usuariosRef.child("servicos")
        servicosRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val totalItens = snapshot.childrenCount.toInt()
                var itensCarregados = 0

                if (snapshot.exists()) {

                    for (servicoSnapshot in snapshot.children) {
                        val nomeServico = servicoSnapshot.child("descricao").getValue(String::class.java)!!
                        val precoServico = servicoSnapshot.child("valor").getValue(Double::class.java)!!

                        listaDeEmpresas.add(Servico(nomeServico, precoServico))
                        itensCarregados++

                        if (itensCarregados == totalItens) {
                            adapter.submitList(listaDeEmpresas)

                        }

                    }
                } else {

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
    private fun ajeitaCalendario(){
        val calendario = Calendar.getInstance()
        calendario.add(Calendar.DAY_OF_MONTH, 1)

        val max = Calendar.getInstance()
        max.add(Calendar.MONTH, 3)

        binding.cvCalendario.minDate = calendario.timeInMillis
        binding.cvCalendario.maxDate = max.timeInMillis

        binding.cvCalendario.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(year, month, dayOfMonth)

            if (selectedCalendar.before(calendario) || selectedCalendar.after(max)) {

                Toast.makeText(this, "Selecione uma data dentro do intervalo permitido.", Toast.LENGTH_SHORT).show()
                binding.cvCalendario.date = System.currentTimeMillis() // Voltar para a data atual
            } else {

            }
        }
    }

    private fun obterItensSelecionados(): List<String> {
        val itensSelecionados = adapter.selectedItems.map {
            it.descricao
        }

        return itensSelecionados
    }

    private fun obterValorTotal(): Double {
        val itensSelecionados = adapter.selectedItems.map {
            it.valor
        }

        return itensSelecionados.sum()
    }
    private fun ajeitaSpinner(){
        val spinner = binding.spnHorarios

        val horarios = mutableListOf<String>()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 10)
        calendar.set(Calendar.MINUTE, 0)

        val maxTime = Calendar.getInstance()
        maxTime.set(Calendar.HOUR_OF_DAY, 21)
        maxTime.set(Calendar.MINUTE, 30)

        while (calendar.before(maxTime) || calendar == maxTime) {
            val horaFormatada = SimpleDateFormat("hh:mm a").format(calendar.time)
            horarios.add(horaFormatada)
            calendar.add(Calendar.MINUTE, 30)
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, horarios)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun setupAdapter(){
        binding.rvServices.adapter = adapter
    }
}