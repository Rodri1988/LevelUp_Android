package com.example.levelup.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.model.RegisterUIState
import com.example.levelup.model.RegisterUIErrors
import com.example.levelup.repository.UserRepository
import com.example.levelup.utils.validateEmail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.levelup.utils.PasswordUtils

class RegisterViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterUIState())
    val state = _state

    fun onChangeUsername(username: String) {
        _state.update {
            it.copy(
                username = username,
                errors = it.errors.copy(
                    usernameError = if (username.isBlank()) "El nombre de usuario no puede estar vacío." else null
                )
            )
        }
    }

    fun onChangeEmail(email: String) {
        _state.update {
            it.copy(
                email = email,
                errors = it.errors.copy(
                    emailError = validateEmail(email)
                )
            )
        }
    }

    fun onChangePassword(password: String) {
        _state.update {
            it.copy(
                password = password,
                errors = it.errors.copy(
                    passwordError = if (password.length < 6) "La contraseña debe tener al menos 6 caracteres" else null,
                    confirmPasswordError = if (it.confirmPassword != password) "Las contraseñas no coinciden" else null
                )
            )
        }
    }

    fun onChangeConfirmPassword(confirmPassword: String) {
        _state.update {
            it.copy(
                confirmPassword = confirmPassword,
                errors = it.errors.copy(
                    confirmPasswordError = if (confirmPassword != it.password) "Las contraseñas no coinciden" else null
                )
            )
        }
    }
    fun onProfileImageSelected(uri: String) {
        _state.update { it.copy(profileImageUri = uri) }
    }

    fun onRegisterSubmit(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val currentState = state.value

        if (currentState.errors.hasErrors() ||
            currentState.username.isBlank() ||
            currentState.email.isBlank() ||
            currentState.password.isBlank() ||
            currentState.confirmPassword.isBlank()

        ) {
            onError("Por favor, corrija todos los errores antes de enviar.")
            return
        }

        viewModelScope.launch {
            try {
                if (userRepository.isEmailExists(currentState.email)) {
                    onError("El correo electrónico ya existe")
                    return@launch
                }

                val userId = userRepository.insertUser(
                    username = currentState.username,
                    email = currentState.email,
                    password = PasswordUtils.hashPassword(currentState.password),
                    profileImageUri = currentState.profileImageUri ?: ""

                )

                if (userId > 0) {
                    onSuccess()
                } else {
                    onError("Registro fallido")
                }
            } catch (e: Exception) {
                onError("Registro fallido: ${e.message}")
            }
        }
    }
}