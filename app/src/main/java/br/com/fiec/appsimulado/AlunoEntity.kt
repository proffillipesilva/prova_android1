package br.com.fiec.appsimulado

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "alunos")
data class AlunoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val foto: String // Guarda a URL da imagem
)
