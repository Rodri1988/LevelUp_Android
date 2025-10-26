package com.example.levelup.model

// Clase para almacenar los errores de cada campo
data class RegisterUIErrors(
    val usernameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null
) {
    // Método para revisar si hay algún error
    fun hasErrors(): Boolean {
        return usernameError != null ||
                emailError != null ||
                passwordError != null ||
                confirmPasswordError != null
    }
}

// Clase para almacenar el estado completo de la UI
data class RegisterUIState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val profileImageUri: String = "",
    val errors: RegisterUIErrors = RegisterUIErrors()
)