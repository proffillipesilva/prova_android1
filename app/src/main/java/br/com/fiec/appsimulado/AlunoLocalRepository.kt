package br.com.fiec.appsimulado

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AlunoLocalRepository(
    private val alunoDao: AlunoDao
) {
    // Retorna o fluxo de alunos convertendo a lista de AlunoEntity para Aluno (Domínio)
    val alunos: Flow<List<Aluno>> = alunoDao.getAlunos().map { entities ->
        entities.map { it.toAluno() }
    }

    // insere um Aluno convertendo para AlunoEntity
    suspend fun insertAluno(aluno: Aluno) {
        alunoDao.insertAluno(aluno.toEntity())
    }
}