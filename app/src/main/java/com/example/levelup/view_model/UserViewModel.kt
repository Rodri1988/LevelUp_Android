package com.example.levelup.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.model.UserEntity
import com.example.levelup.repository.UserRepository
import com.example.levelup.utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UserProfileState(
    val user: UserEntity? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

class UserViewModel(
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _profileState = MutableStateFlow(UserProfileState())
    val profileState: StateFlow<UserProfileState> = _profileState.asStateFlow()

    init {
        loadUserProfile()
    }

    fun loadUserProfile() {
        viewModelScope.launch {
            _profileState.value = UserProfileState(isLoading = true)
            try {
                val userId = sessionManager.getUserId()
                if (userId != -1) {
                    val user = userRepository.getUserById(userId)
                    if (user != null) {
                        _profileState.value = UserProfileState(
                            user = user,
                            isLoading = false
                        )
                    } else {
                        _profileState.value = UserProfileState(
                            isLoading = false,
                            error = "Usuario no encontrado"
                        )
                    }
                } else {
                    _profileState.value = UserProfileState(
                        isLoading = false,
                        error = "No hay sesión activa"
                    )
                }
            } catch (e: Exception) {
                _profileState.value = UserProfileState(
                    isLoading = false,
                    error = "Error al cargar perfil: ${e.message}"
                )
            }
        }
    }

    fun logout(onLogoutSuccess: () -> Unit) {
        sessionManager.clearSession()
        onLogoutSuccess()
    }

    fun isUserLoggedIn(): Boolean {
        return sessionManager.isLoggedIn()
    }

    fun updateProfileImage(newImageUri: String) {
        viewModelScope.launch {
            try {
                val currentUser = _profileState.value.user
                if (currentUser != null) {
                    userRepository.updateProfileImage(currentUser.uid, newImageUri)
                    _profileState.value = _profileState.value.copy(
                        user = currentUser.copy(profileImageUri = newImageUri)
                    )
                }
            } catch (e: Exception) {
                _profileState.value = _profileState.value.copy(
                    error = "Error al actualizar foto: ${e.message}"
                )
            }
        }
    }
}