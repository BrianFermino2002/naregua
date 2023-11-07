package com.example.naregua

data class AgendamentoEmpresa (
    val idAgendamento: String,
    val nomeUser: String,
    val dia: Int,
    val mes: Int,
    val ano: Int,
    val Horario: String,
    val valor: Double
)