package com.example.levelup.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(
        "user_session",
        Context.MODE_PRIVATE
    )

    companion object {
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USERNAME = "username"
        private const val KEY_EMAIL = "email"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
    }

    // Guardar sesión
    fun saveUserSession(userId: Int, username: String, email: String) {
        prefs.edit().apply {
            putInt(KEY_USER_ID, userId)
            putString(KEY_USERNAME, username)
            putString(KEY_EMAIL, email)
            putBoolean(KEY_IS_LOGGED_IN, true)
            apply()
        }
    }

    // Obtener datos de sesión
    fun getUserId(): Int = prefs.getInt(KEY_USER_ID, -1)
    fun getUsername(): String? = prefs.getString(KEY_USERNAME, null)
    fun getEmail(): String? = prefs.getString(KEY_EMAIL, null)
    fun isLoggedIn(): Boolean = prefs.getBoolean(KEY_IS_LOGGED_IN, false)

    // Cerrar sesión
    fun clearSession() {
        prefs.edit().clear().apply()
    }
}