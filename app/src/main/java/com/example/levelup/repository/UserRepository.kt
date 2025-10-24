package com.example.levelup.repository

import com.example.levelup.model.UserDao
import com.example.levelup.model.UserEntity

class UserRepository(private val userDao: UserDao) {

    suspend fun insertUser(email: String, password: String): Long {
        val user = UserEntity(email = email, password = password)
        return userDao.insert(user)
    }

    suspend fun getUserByEmail(email: String): UserEntity? {
        return userDao.findByEmail(email)
    }

    suspend fun isEmailExists(email: String): Boolean {
        return userDao.findByEmail(email) != null
    }
}