package com.example.naregua

import android.os.Parcel
import android.os.Parcelable

data class Agendamento(
    val idUser: String,
    val idEmpresa: String,
    val valor: Double,
    val servicos: List<String>,
    val dia: Int,
    val mes: Int,
    val ano: Int,
    val horario: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.createStringArrayList() ?: emptyList(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idUser)
        parcel.writeString(idEmpresa)
        parcel.writeDouble(valor)
        parcel.writeStringList(servicos)
        parcel.writeInt(dia)
        parcel.writeInt(mes)
        parcel.writeInt(ano)
        parcel.writeString(horario)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Agendamento> {
        override fun createFromParcel(parcel: Parcel): Agendamento {
            return Agendamento(parcel)
        }

        override fun newArray(size: Int): Array<Agendamento?> {
            return arrayOfNulls(size)
        }
    }
}