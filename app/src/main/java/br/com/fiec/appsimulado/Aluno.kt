package br.com.fiec.appsimulado

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "alunos")
data class Aluno(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Chave primária automática
    val nome: String,
    val foto: String
)
