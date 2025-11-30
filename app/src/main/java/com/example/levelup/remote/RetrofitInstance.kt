package com.example.levelup.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL_POSTS = "https://jsonplaceholder.typicode.com"
    private const val BASE_URL_INDICADORES = "https://mindicador.cl/api/"

    // API para posts
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_POSTS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    // API para indicadores
    val apiIndicadores: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_INDICADORES)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}