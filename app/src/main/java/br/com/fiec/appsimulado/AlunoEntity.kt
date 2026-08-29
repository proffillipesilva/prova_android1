package br.com.fiec.appsimulado
import androidx.room.PrimaryKey
import androidx.room3.Entity
import androidx.room3.PrimaryKey

   


@Entity(tableName = "alunos")
data class AlunoEntityvar(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nome: String,
    val email: String
)
