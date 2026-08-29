package br.com.fiec.appsimulado

class AlunoRemoteRepository(private val apiService: AlunoApiService) {
    suspend fun salvarRemoto(aluno: Aluno) = apiService.inserirAluno(aluno)
    suspend fun getAlunosRemotos() = apiService.getAlunos()
}