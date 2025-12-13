package com.example.levelup.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.model.RegisterUIState
import com.example.levelup.model.UserEntity
import com.example.levelup.repository.UserRepository
import com.example.levelup.utils.PasswordUtils
import com.example.levelup.utils.validateEmail
import com.example.levelup.utils.validatePassword
import com.example.levelup.utils.validateUsername
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterUIState())
    val state = _state

    fun onChangeUsername(username: String) {
        _state.update {
            it.copy(
                username = username,
                errors = it.errors.copy(usernameError = validateUsername(username))
            )
        }
    }

    fun onChangeEmail(email: String) {
        _state.update {
            it.copy(
                email = email,
                errors = it.errors.copy(emailError = validateEmail(email))
            )
        }
    }

    fun onChangePassword(password: String) {
        _state.update {
            it.copy(
                password = password,
                errors = it.errors.copy(passwordError = validatePassword(password))
            )
        }
    }

    fun onChangeConfirmPassword(confirmPassword: String) {
        _state.update {
            it.copy(
                confirmPassword = confirmPassword,
                errors = it.errors.copy(
                    confirmPasswordError = if (confirmPassword != it.password) {
                        "Las contraseñas no coinciden"
                    } else null
                )
            )
        }
    }

    fun onChangeProfileImageUri(uri: String) {
        _state.update { it.copy(profileImageUri = uri) }
    }

    fun onRegisterSubmit(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val currentState = state.value

        // Validar que no haya errores
        if (currentState.errors.hasErrors()) {
            onError("Por favor corrija los errores en el formulario")
            return
        }

        // Validar que las contraseñas coincidan
        if (currentState.password != currentState.confirmPassword) {
            onError("Las contraseñas no coinciden")
            return
        }

        viewModelScope.launch {
            try {
                // Verificar si el email ya existe
                val existingUser = userRepository.getUserByEmail(currentState.email)

                if (existingUser != null) {
                    onError("Este email ya está registrado")
                    return@launch
                }

                // Hashear la contraseña antes de guardar
                val hashedPassword = PasswordUtils.hashPassword(currentState.password)

                // Crear nuevo usuario
                val newUser = UserEntity(
                    username = currentState.username,
                    email = currentState.email,
                    password = hashedPassword, // ✅ Guardar contraseña hasheada
                    profileImageUri = currentState.profileImageUri
                )

                userRepository.insertUser(newUser)
                onSuccess()

            } catch (e: Exception) {
                onError("Error al registrar: ${e.message}")
            }
        }
    }
}