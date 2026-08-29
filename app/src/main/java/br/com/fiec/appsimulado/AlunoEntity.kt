package br.com.fiec.appsimulado

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "aluno_table")
data class AlunoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val foto: String
)

fun AlunoEntity.toDomain(): Aluno {
    return Aluno(
        id = this.id,
        nome = this.nome,
        foto = this.foto
    )
}

fun Aluno.toEntity(): AlunoEntity {
    return AlunoEntity(
        id = this.id,
        nome = this.nome,
        foto = this.foto
    )
}