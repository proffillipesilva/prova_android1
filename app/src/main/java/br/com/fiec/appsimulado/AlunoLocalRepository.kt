package com.example.alunoapp.data.repository

import com.example.alunoapp.data.local.AlunoDao
import br.edu.provap1.coisas.toAluno
import br.edu.provap1.coisas.toEntity
import com.example.alunoapp.model.Aluno
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Repositório responsável por toda a interação com os dados locais
 * (Room), trabalhando com AlunoEntity internamente e expondo o
 * modelo de domínio Aluno para o resto do app.
 */
class AlunoLocalRepository(private val alunoDao: AlunoDao) {

    fun getAlunos(): Flow<List<Aluno>> {
        return alunoDao.getAll().map { entidades -> entidades.map { it.toAluno() } }
    }

    suspend fun insertAluno(aluno: Aluno) {
        alunoDao.insert(aluno.toEntity())
    }

    suspend fun insertAlunos(alunos: List<Aluno>) {
        alunoDao.insertAll(alunos.map { it.toEntity() })
    }

    suspend fun deleteAluno(aluno: Aluno) {
        alunoDao.delete(aluno.toEntity())
    }

    suspend fun deleteAll() {
        alunoDao.deleteAll()
    }
}
