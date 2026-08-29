package br.edu.provap1.coisas

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.alunoapp.model.Aluno

@Entity(tableName = "aluno")
data class AlunoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val curso: String,
    val foto: String
)

// Mapeadores entre a Entity (banco) e o modelo de domínio (Aluno)

fun AlunoEntity.toAluno(): Aluno {
    return Aluno(
        id = id,
        nome = nome,
        curso = curso,
        foto = foto
    )
}

fun Aluno.toEntity(): AlunoEntity {
    return AlunoEntity(
        id = id,
        nome = nome,
        curso = curso,
        foto = foto
    )
}
