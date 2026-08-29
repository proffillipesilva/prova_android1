package br.com.fiec.appsimulado

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlunoRemoteRepository(private val apiService: AlunoApiService) {

    // Busca os alunos da API na nuvem
    suspend fun fetchAlunos(): List<Aluno> = withContext(Dispatchers.IO) {
        // Se no seu Service você usou a versão com 'suspend fun getAlunos(): List<Aluno>'
        apiService.getAlunos() as List<Aluno>

        // NOTA: Se o seu AlunoApiService estiver retornando 'Call<List<Aluno>>', use a linha abaixo:
        // apiService.getAlunos().execute().body() ?: emptyList()
    }

    // Insere um novo aluno na API da nuvem
    suspend fun insertAlunoRemote(aluno: Aluno): Aluno? = withContext(Dispatchers.IO) {
        // Se no seu Service você usou a versão com 'suspend fun insertAluno'
        apiService.insertAluno(aluno)

        // NOTA: Se o seu AlunoApiService estiver retornando 'Call<Aluno>', use a linha abaixo:
        // apiService.insertAluno(aluno).execute().body()
    }
}