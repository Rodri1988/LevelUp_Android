package com.example.levelup.model

data class LoginUIState(
    val email: String = "",
    val password: String = "",
    val errors: LoginError = LoginError()
) {

}

data class LoginError(
    val emailError: String? = null,
    val passwordError: String? = null
){
    fun hasErrors(): Boolean {
        return emailError != null || passwordError != null
    }
}