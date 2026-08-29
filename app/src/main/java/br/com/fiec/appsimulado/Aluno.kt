package com.example.alunoapp.model
data class Aluno(
    val id: Int = 0,
    val nome: String,
    val curso: String,
    val foto: String // pode ser uma URL da foto ou um caminho local
)
