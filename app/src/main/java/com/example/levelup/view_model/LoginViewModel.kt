package com.example.levelup.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.model.LoginUIState
import com.example.levelup.repository.UserRepository
import com.example.levelup.utils.validateEmail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel (
    private val userRepository: UserRepository
) : ViewModel() {
    private val _state = MutableStateFlow(LoginUIState())

    val state = _state

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
            )
        }
    }

    fun onLoginSubmit(onSuccess: () -> Unit, onError: (String) -> Unit) {
        val currentState = state.value

        if (currentState.errors.hasErrors() || currentState.email.isEmpty() || currentState.password.isEmpty()) {
            onError("Please fix all errors before submitting")
            return
        }

        viewModelScope.launch {
            try {
                val user = userRepository.getUserByEmail(currentState.email)
                if (user != null && user.password == currentState.password) {
                    onSuccess()
                } else {
                    onError("Invalid email or password")
                }
            } catch (e: Exception) {
                onError("Login failed: ${e.message}")
            }
        }
    }
}