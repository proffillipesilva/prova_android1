package br.com.fiec.appsimulado

import com.google.gson.annotations.SerializedName

data class Aluno(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("nome")
    val nome: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("foto")
    val foto: String
)