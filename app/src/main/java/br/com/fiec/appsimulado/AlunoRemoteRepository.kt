package br.com.fiec.appsimulado

class AlunoRemoteRepository(private val api: AlunoApiService) {

    suspend fun getAlunos(): List<Aluno> = api.getAlunos()

    suspend fun insertAluno(aluno: Aluno): Aluno = api.insertAluno(aluno)
}