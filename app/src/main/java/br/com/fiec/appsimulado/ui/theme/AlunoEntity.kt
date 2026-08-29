package br.com.fiec.appsimulado.ui.theme

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "tabela_alunos")
data class AlunoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "nome_aluno")
    val nome: String,

    @ColumnInfo(name = "email_aluno")
    val email: String,

    @ColumnInfo(name = "foto_uri")
    val foto: String // Guardará o caminho/URI local da imagem
)
