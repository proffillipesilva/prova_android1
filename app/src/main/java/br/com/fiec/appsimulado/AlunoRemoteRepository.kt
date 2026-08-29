package br.com.fiec.appsimulado

import br.com.fiec.appsimulado.ui.theme.Aluno

class AlunoRemoteRepository(private val apiService: AlunoApiService) {

    suspend fun getAlunos(): List<Aluno> {
        return apiService.getAlunos()
    }

    suspend fun salvar(aluno: Aluno): Aluno {
        return apiService.insertAluno(aluno)
    }
}