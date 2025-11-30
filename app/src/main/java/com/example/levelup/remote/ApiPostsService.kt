package com.example.levelup.remote

import com.example.levelup.model.Post
import retrofit2.http.GET

interface ApiPostsService {

    @GET("posts")
    suspend fun getPosts(): List<Post>
}
