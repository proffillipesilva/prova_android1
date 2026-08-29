package br.com.fiec.appsimulado

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alunos")
data class AlunoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "nome")
    val nome: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "foto")
    val foto: String
)