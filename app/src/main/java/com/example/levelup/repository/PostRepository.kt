package com.example.levelup.repository

import com.example.levelup.model.Post
import com.example.levelup.remote.RetrofitInstance

class PostRepository {
    suspend fun getPosts(): List<Post>{
        return RetrofitInstance.api.getPosts()
    }
}