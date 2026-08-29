package br.com.fiec.appsimulado

class AlunoRemoteRepository(
private val apiService: AlunoApiService
) {
    suspend fun getAlunos(): List<Aluno> {
        return apiService.getAlunos()
    }

    suspend fun insertAluno(aluno: Aluno): Aluno {
        return apiService.insertAluno(aluno)
    }
}