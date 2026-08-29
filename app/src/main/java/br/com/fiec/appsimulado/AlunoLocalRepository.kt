package br.com.fiec.appsimulado

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AlunoLocalRepository(private val alunoDao: AlunoDao) {

    val alunos: Flow<List<Aluno>> = alunoDao.getAllAlunos().map { entities ->
        entities.map { it.toDomain() }
    }

    suspend fun insert(aluno: Aluno) {
        alunoDao.insert(aluno.toEntity())
    }

    suspend fun insertAll(alunos: List<Aluno>) {
        alunoDao.insertAll(alunos.map { it.toEntity() })
    }
}