package br.com.fiec.appsimulado.ui.theme

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query

@Dao
interface AlunoDao {

    @Query("SELECT * FROM tabela_alunos")
    suspend fun getAlunosLocais(): List<AlunoEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirAluno(aluno: AlunoEntity)

}