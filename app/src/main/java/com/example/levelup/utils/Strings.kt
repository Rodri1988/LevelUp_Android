package com.example.levelup.utils

import android.util.Patterns

fun validateEmail(email: String): String? {
    return when {
        email.isEmpty() -> "El email es requerido"
        !isValidEmail(email) -> "El formato del email no es válido"
        else -> null
    }
}

// ✅ FUNCIÓN CORREGIDA: Ahora permite el símbolo + en emails
fun isValidEmail(email: String): Boolean {
    return try {
        // Intenta usar Patterns de Android (funciona en la app real)
        Patterns.EMAIL_ADDRESS.matcher(email).matches()
    } catch (e: Exception) {
        // Fallback para tests unitarios donde Patterns es null
        // ✅ CAMBIO: Agregado el + en el regex: [a-zA-Z0-9._+-]+
        email.matches(Regex("[a-zA-Z0-9._+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))
    }
}

fun validatePassword(password: String): String? {
    return when {
        password.isEmpty() -> "La contraseña es requerida"
        password.length < 6 -> "La contraseña debe tener al menos 6 caracteres"
        else -> null
    }
}

fun validateUsername(username: String): String? {
    return when {
        username.isEmpty() -> "El nombre de usuario es requerido"
        username.length < 3 -> "El nombre debe tener al menos 3 caracteres"
        username.length > 20 -> "El nombre de usuario no puede tener más de 20 caracteres"
        !username.matches(Regex("^[a-zA-Z0-9_]+$")) -> "El nombre de usuario solo puede contener letras, números y guiones bajos"
        else -> null
    }
}