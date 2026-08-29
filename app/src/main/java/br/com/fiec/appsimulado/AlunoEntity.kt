package br.com.fiec.appsimulado

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "aluno")
data class AlunoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val foto: String
)

// === ADICIONE AS FUNÇÕES DE EXTENSÃO ABAIXO ===

// Converte do modelo de banco de dados (Room) para o modelo de domínio
fun AlunoEntity.toAluno(): Aluno {
    return Aluno(
        nome = this.nome,
        foto = this.foto
    )
}

// Converte do modelo de domínio para o modelo de banco de dados (Room)
fun Aluno.toEntity(id: Int = 0): AlunoEntity {
    return AlunoEntity(
        id = id,
        nome = this.nome,
        foto = this.foto
    )
}