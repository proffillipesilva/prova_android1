package br.com.fiec.appsimulado

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AlunoLocalRepository(
    private val alunoDao: AlunoDao
) {

    fun getAlunos(): Flow<List<Aluno>> {
        return alunoDao.getAlunos().map { entities ->
            entities.map { it.toAluno() }
        }
    }

    suspend fun getAlunoById(id: Int): Aluno? {
        return alunoDao.getAlunoById(id)?.toAluno()
    }

    suspend fun insertAluno(aluno: Aluno) {
        alunoDao.insertAluno(aluno.toEntity())
    }

    suspend fun updateAluno(aluno: Aluno) {
        alunoDao.updateAluno(aluno.toEntity())
    }

    suspend fun deleteAluno(aluno: Aluno) {
        alunoDao.deleteAluno(aluno.toEntity())
    }
}