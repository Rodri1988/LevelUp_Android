package com.example.levelup.view_model



import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.levelup.repository.UserRepository
import com.example.levelup.utils.SessionManager

class ViewModelFactory(
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager  //
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // LoginViewModel
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(userRepository, sessionManager) as T  // 🆕
        }
        // RegisterViewModel
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(userRepository) as T
        }
        // UserViewModel - 🆕 NUEVO
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(userRepository, sessionManager) as T
        }
        // Si piden otro, error
        throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
    }
}