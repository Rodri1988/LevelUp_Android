package com.example.levelup.model

data class RegisterUIState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val profileImageUri: String = "",
    val errors: RegisterErrors = RegisterErrors()
)

data class RegisterErrors(
    val usernameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null
) {
    fun hasErrors(): Boolean =
        usernameError != null ||
                emailError != null ||
                passwordError != null ||
                confirmPasswordError != null
}