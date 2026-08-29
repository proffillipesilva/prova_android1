package br.com.fiec.appsimulado

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AlunoApiService {

    @GET("alunos") // Substitua pelo endpoint correto se o professor especificou
    fun getAlunos(): Call<List<Aluno>>

    @POST("alunos") // Substitua pelo endpoint correto se o professor especificou
    fun insertAluno(@Body aluno: Aluno): Aluno?
}