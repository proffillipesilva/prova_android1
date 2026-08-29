package br.com.fiec.appsimulado

import kotlinx.coroutines.flow.Flow

class AlunoLocalRepository(private val alunoDao: AlunoDao) {

    val alunos: Flow<List<AlunoEntity>> = alunoDao.getAll()

    suspend fun salvar(aluno: AlunoEntity) {
        alunoDao.insert(aluno)
    }
}