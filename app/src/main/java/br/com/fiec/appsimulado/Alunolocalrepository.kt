package br.com.fiec.appsimulado

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AlunoLocalRepository(private val alunoDao: AlunoDao) {

    fun getAlunos(): Flow<List<Aluno>> {
        return alunoDao.getAlunos().map { lista -> lista.map { it.toAluno() } }
    }

    suspend fun insertAluno(aluno: Aluno) {
        alunoDao.insertAluno(aluno.toEntity())
    }

    suspend fun insertAlunos(alunos: List<Aluno>) {
        alunoDao.insertAlunos(alunos.map { it.toEntity() })
    }
}
