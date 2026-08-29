package br.com.fiec.appsimulado

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query

@Dao
interface AlunoDao {

    // Retorna todos os alunos do banco local
    @Query("SELECT * FROM alunos")
    fun getAlunos(): List<Aluno>
    // Dica: Se usar Coroutines na matéria, use: suspend fun getAlunos(): List<Aluno>

    // Insere um aluno. OnConflictStrategy.REPLACE atualiza se o ID já existir
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAluno(aluno: AlunoEntity)
    // Dica: Se usar Coroutines na matéria, use: suspend fun insertAluno(aluno: Aluno)
}