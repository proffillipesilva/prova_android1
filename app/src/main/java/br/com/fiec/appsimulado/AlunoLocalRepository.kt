package br.com.fiec.appsimulado

import kotlinx.coroutines.flow.Flow

class AlunoLocalRepository(private val dao: AlunoDao) {
    suspend fun salvarLocal(aluno: AlunoEntity) = dao.inserir(aluno)
    fun getAlunosLocais(): Flow<List<AlunoEntity>> = dao.getTodosAlunos()
}