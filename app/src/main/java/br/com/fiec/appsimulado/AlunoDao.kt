package br.com.fiec.appsimulado

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query

@Dao
interface AlunoDao {

    @Query("SELECT * FROM aluno")
    suspend fun getAlunos(): List<AlunoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAluno(aluno: AlunoEntity)
}