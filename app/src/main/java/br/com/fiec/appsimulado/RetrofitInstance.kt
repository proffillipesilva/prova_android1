package com.example.alunoapp.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    // TODO: troque pela URL real da sua API
    private const val BASE_URL = "https://seu-backend.com/api/"

    val api: AlunoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AlunoApiService::class.java)
    }
}
