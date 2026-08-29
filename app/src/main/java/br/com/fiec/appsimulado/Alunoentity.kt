package br.com.fiec.appsimulado

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "aluno")
data class AlunoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val email: String,
    val foto: String
)

// Mapeamento entre a entidade do Room e o modelo de domínio Aluno.
fun AlunoEntity.toAluno(): Aluno = Aluno(id = id, nome = nome, email = email, foto = foto)

fun Aluno.toEntity(): AlunoEntity = AlunoEntity(id = id, nome = nome, email = email, foto = foto)