package br.com.fiec.appsimulado

class AlunoRemoteRepository(
    private val alunoApiService: AlunoApiService
) {

    suspend fun getAlunos(): Result<List<Aluno>> {
        return try {
            val response = alunoApiService.getAlunos()
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Erro ao buscar alunos: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun insertAluno(aluno: Aluno): Result<Aluno> {
        return try {
            val response = alunoApiService.insertAluno(aluno)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Resposta vazia ao inserir aluno"))
            } else {
                Result.failure(Exception("Erro ao inserir aluno: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}