package com.example.levelup.utils

import android.util.Patterns

fun validateEmail(email: String): String? {
    return when {
        email.isBlank() -> "El email es requerido"
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Email inválido"
        else -> null
    }
}

fun validatePassword(password: String): String? {
    return when {
        password.isBlank() -> "La contraseña es requerida"
        password.length < 6 -> "La contraseña debe tener al menos 6 caracteres"
        else -> null
    }
}

fun validateUsername(username: String): String? {
    return when {
        username.isBlank() -> "El nombre de usuario es requerido"
        username.length < 3 -> "El nombre debe tener al menos 3 caracteres"
        else -> null
    }
}