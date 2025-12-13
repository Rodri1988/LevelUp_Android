package com.example.levelup.repository

import com.example.levelup.model.UserEntity

class UserRepository(private val userDao: UserDao) {

    suspend fun getAllUsers(): List<UserEntity> {
        return userDao.getAll()
    }

    suspend fun getUserByEmail(email: String): UserEntity? {
        return userDao.findByEmail(email)
    }

    suspend fun login(email: String, password: String): UserEntity? {
        return userDao.login(email, password)
    }

    // ✅ Método que usa tu LoginViewModel
    suspend fun validateLogin(email: String, hashedPassword: String): Boolean {
        val user = userDao.findByEmail(email)
        return user != null && user.password == hashedPassword
    }

    suspend fun insertUser(user: UserEntity): Long {
        return userDao.insert(user)
    }

    suspend fun deleteUser(user: UserEntity) {
        userDao.delete(user)
    }

    suspend fun updateProfileImage(userId: Int, newImageUri: String) {
        userDao.updateProfileImage(userId, newImageUri)
    }

    suspend fun getUserById(userId: Int): UserEntity? {
        return userDao.getUserById(userId)
    }

    suspend fun updateUser(user: UserEntity) {
        userDao.updateUser(user)
    }
}