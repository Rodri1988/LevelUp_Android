package com.example.levelup.remote

import com.example.levelup.model.IndicadoresResponse
import com.example.levelup.model.Post
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    // Para posts (ya lo tienes)
    @GET("/posts")
    suspend fun getPosts(): List<Post>

    // Para indicadores (NUEVO)
    @GET("/")
    suspend fun getIndicadores(): Response<IndicadoresResponse>
}