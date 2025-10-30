package com.example.levelup.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.model.LoginUIState
import com.example.levelup.repository.UserRepository
import com.example.levelup.utils.validateEmail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.levelup.utils.PasswordUtils

class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginUIState())
    val state = _state

    /** Actualiza el email del usuario */
    fun onChangeEmail(email: String) {
        _state.update {
            it.copy(
                email = email,
                errors = it.errors.copy(emailError = validateEmail(email))
            )
        }
    }

    /** Actualiza la contraseña del usuario */
    fun onChangePassword(password: String) {
        _state.update {
            it.copy(
                password = password,
                errors = it.errors.copy(
                    passwordError = if (password.length < 6) "La contraseña debe tener al menos 6 caracteres" else null
                )
            )
        }
    }

    /**
     * Función para iniciar sesión.
     * @param onSuccess Devuelve el username si login es exitoso.
     * @param onError Devuelve mensaje de error si falla el login.
     */
    fun onLoginSubmit(
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        val currentState = state.value

        // Validación de campos
        if (currentState.email.isBlank() || currentState.password.isBlank() || currentState.errors.hasErrors()) {
            onError("Por favor complete todos los campos correctamente")
            return
        }

        // Ejecutar login en coroutine
        viewModelScope.launch {
            try {
                val valid = userRepository.validateLogin(
                    currentState.email,
                    PasswordUtils.hashPassword(currentState.password)
                )
                if (valid) {
                    // Llamada suspendida dentro de coroutine
                    val username = userRepository.getUsernameByEmail(currentState.email)
                    onSuccess(username) // Devuelve username al IndexScreen
                } else {
                    onError("Credenciales incorrectas")
                }
            } catch (e: Exception) {
                onError("Error al iniciar sesión: ${e.message}")
            }
        }
    }
}