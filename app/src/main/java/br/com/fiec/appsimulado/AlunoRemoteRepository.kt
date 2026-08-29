package com.example.alunoapp.data.repository

import com.example.alunoapp.data.remote.AlunoApiService
import com.example.alunoapp.model.Aluno

/**
 * Repositório responsável por toda a interação com o backend remoto,
 * usando o AlunoApiService (Retrofit).
 */
class AlunoRemoteRepository(private val apiService: AlunoApiService) {

    suspend fun getAlunos(): List<Aluno> {
        return apiService.getAlunos()
    }

    suspend fun insertAluno(aluno: Aluno): Aluno {
        return apiService.insertAluno(aluno)
    }
}
