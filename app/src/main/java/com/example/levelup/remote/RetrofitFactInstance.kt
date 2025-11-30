package com.example.levelup.remote

import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Modelo para la respuesta JSON
data class UselessFactResponse(
    @SerializedName("text")
    val text: String
)

interface FactsApi {
    @GET("api/v2/facts/random?language=en")
    suspend fun getRandomFact(): UselessFactResponse
}

object RetrofitFactInstance {
    val api: FactsApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://uselessfacts.jsph.pl/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FactsApi::class.java)
    }
}