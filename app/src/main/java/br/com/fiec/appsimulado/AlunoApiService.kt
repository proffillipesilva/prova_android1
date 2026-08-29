package com.example.alunoapp.data.remote

import com.example.alunoapp.model.Aluno
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AlunoApiService {

    @GET("alunos")
    suspend fun getAlunos(): List<Aluno>

    @POST("alunos")
    suspend fun insertAluno(@Body aluno: Aluno): Aluno
}
