package com.example.levelup.repository

import com.example.levelup.model.UserDao
import com.example.levelup.model.UserEntity

class UserRepository(private val userDao: UserDao) {

    /** Inserta un nuevo usuario en la base de datos */
    suspend fun insertUser(username: String, email: String, password: String): Long {
        val user = UserEntity(username = username, email = email, password = password)
        return userDao.insert(user)
    }

    /** Verifica si ya existe un usuario con el mismo email */
    suspend fun isEmailExists(email: String): Boolean {
        return userDao.findByEmail(email) != null
    }

    /** Valida el login con email y contraseña */
    suspend fun validateLogin(email: String, password: String): Boolean {
        return userDao.login(email, password) != null
    }

    /** Obtiene el nombre de usuario por email */
    suspend fun getUsernameByEmail(email: String): String {
        return userDao.findByEmail(email)?.username ?: ""
    }

    /** Obtiene todos los usuarios (opcional, para pruebas) */
    suspend fun getAllUsers(): List<UserEntity> {
        return userDao.getAll()
    }

    /** Elimina un usuario (opcional) */
    suspend fun deleteUser(user: UserEntity) {
        userDao.delete(user)
    }
}