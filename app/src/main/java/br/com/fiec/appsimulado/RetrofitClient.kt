package br.com.fiec.appsimulado

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.exemplo.com/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val alunoApiService: AlunoApiService by lazy {
        retrofit.create(AlunoApiService::class.java)
    }
}