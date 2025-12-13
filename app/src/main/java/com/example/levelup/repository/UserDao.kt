package com.example.levelup.repository

import androidx.room.*
import com.example.levelup.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    suspend fun getAll(): List<UserEntity>

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun findByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): UserEntity?

    @Insert
    suspend fun insert(user: UserEntity): Long

    @Delete
    suspend fun delete(user: UserEntity)

    @Query("UPDATE users SET profile_image_uri = :newImageUri WHERE uid = :userId")
    suspend fun updateProfileImage(userId: Int, newImageUri: String)

    // Métodos adicionales que pueden ser útiles
    @Query("SELECT * FROM users WHERE uid = :userId LIMIT 1")
    suspend fun getUserById(userId: Int): UserEntity?

    @Update
    suspend fun updateUser(user: UserEntity)
}