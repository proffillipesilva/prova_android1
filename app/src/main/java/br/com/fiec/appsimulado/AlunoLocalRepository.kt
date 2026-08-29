package br.com.fiec.appsimulado

import kotlinx.coroutines.flow.Flow

class AlunoLocalRepository(private val dao: AlunoDao) {

    fun listarTodos(): Flow<List<AlunoEntity>> = dao.listarTodos()

    suspend fun buscarPorId(id: Int): AlunoEntity? = dao.buscarPorId(id)

    suspend fun inserir(aluno: AlunoEntity) = dao.inserir(aluno)

    suspend fun deletar(aluno: AlunoEntity) = dao.deletar(aluno)
}