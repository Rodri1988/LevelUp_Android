package com.example.levelup.model

data class RegisterUIState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val errors: RegisterError = RegisterError()
) {

}

data class RegisterError (
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null
) {
    fun hasErrors(): Boolean {
        return emailError != null || passwordError != null || confirmPasswordError != null
    }
}