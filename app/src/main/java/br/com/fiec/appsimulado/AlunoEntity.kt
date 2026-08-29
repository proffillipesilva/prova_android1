package br.com.fiec.appsimulado

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alunos")
data class AlunoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val foto: String
)

// Funções de extensão para conversão entre Entity e Modelo de Domínio
fun AlunoEntity.toDomain() = Aluno(id = id, nome = nome, foto = foto)
fun Aluno.toEntity() = AlunoEntity(id = id, nome = nome, foto = foto)