package com.example.levelup.model

data class LoginUIState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val errors: LoginUIErrors = LoginUIErrors()
)

data class LoginUIErrors(
    val usernameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null
) {
    fun hasErrors(): Boolean {
        return usernameError != null || emailError != null || passwordError != null
    }
}