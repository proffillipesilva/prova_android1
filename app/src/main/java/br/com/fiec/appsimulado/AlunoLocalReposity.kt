package br.com.fiec.appsimulado

import kotlinx.coroutines.flow.Flow

interface AlunoLocalRepository {
    fun getAlunos(): Flow<List<Aluno>>
    suspend fun getAlunoById(id: Long): Aluno?
    suspend fun insertAluno(aluno: Aluno): Long
    suspend fun insertAlunos(alunos: List<Aluno>)
    suspend fun updateAluno(aluno: Aluno)
    suspend fun deleteAluno(aluno: Aluno)
}
