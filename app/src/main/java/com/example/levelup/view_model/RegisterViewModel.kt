package com.example.levelup.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.model.RegisterUIState
import com.example.levelup.repository.UserRepository
import com.example.levelup.utils.validateEmail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userRepository: UserRepository
): ViewModel() {
    private val _state = MutableStateFlow(RegisterUIState())

    val state = _state

    fun onChangeEmail(email: String) {
        _state.update { it.copy(
            email = email,
            errors = it.errors.copy(emailError = validateEmail(email))
        ) }
    }

    fun onChangePassword(password: String) {
        _state.update { it.copy(
            password = password,
            errors = it.errors.copy(passwordError = if (password.length < 6) "Password must be at least 6 characters" else null)
        ) }
    }

    fun onChangeConfirmPassword(confirmPassword: String) {
        _state.update { it.copy(
            confirmPassword = confirmPassword,
            errors = it.errors.copy(confirmPasswordError = if (confirmPassword != state.value.password) { "Passwords don't match" } else { null })
        ) }
    }

    fun onRegisterSubmit(onSuccess: () -> Unit, onError: (String) -> Unit) {
        val currentState = state.value

        if (currentState.errors.hasErrors() || currentState.email.isEmpty() || currentState.password.isEmpty()) {
            onError("Please fix all errors before submitting")
            return
        }

        viewModelScope.launch {
            try {
                // Check if email already exists
                if (userRepository.isEmailExists(currentState.email)) {
                    onError("Email already exists")
                    return@launch
                }

                // Insert new user
                val userId = userRepository.insertUser(currentState.email, currentState.password)
                if (userId > 0) {
                    onSuccess()
                } else {
                    onError("Registration failed")
                }
            } catch (e: Exception) {
                onError("Registration failed: ${e.message}")
            }
        }
    }
}