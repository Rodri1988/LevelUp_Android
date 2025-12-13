package com.example.levelup.model

data class LoginUIState(
    val email: String = "",
    val password: String = "",
    val errors: LoginErrors = LoginErrors()
)

data class LoginErrors(
    val emailError: String? = null,
    val passwordError: String? = null
) {
    fun hasErrors(): Boolean = emailError != null || passwordError != null
}