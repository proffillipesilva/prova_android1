package br.com.fiec.appsimulado

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Extra (não pedido, mas necessário): sem isso não dá pra instanciar o
 * AlunoApiService. Troque BASE_URL pela URL real da sua API.
 */
object RetrofitInstance {

    private const val BASE_URL = "https://sua-api.com/" // TODO: ajuste aqui

    val api: AlunoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AlunoApiService::class.java)
    }
}
