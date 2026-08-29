package br.com.fiec.appsimulado

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://sua-api.com/api/" // Substitua pela URL base da sua API

    val alunoApiService: AlunoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AlunoApiService::class.java)
    }
}
