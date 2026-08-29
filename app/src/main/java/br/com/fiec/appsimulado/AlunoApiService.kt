package br.com.fiec.appsimulado

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AlunoApiService {

    @GET("alunos")
    suspend fun getAlunos(): Response<List<Aluno>>

    @POST("alunos")
    suspend fun insertAluno(@Body aluno: Aluno): Response<Aluno>
}