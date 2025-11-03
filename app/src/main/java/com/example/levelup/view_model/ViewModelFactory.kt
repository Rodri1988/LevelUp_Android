package com.example.levelup.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.levelup.repository.UserRepository
import com.example.levelup.repository.ProductRepository
import com.example.levelup.utils.SessionManager

class ViewModelFactory(
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository,
    private val sessionManager: SessionManager
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // LoginViewModel
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(userRepository, sessionManager) as T
        }
        // RegisterViewModel
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(userRepository) as T
        }
        // ProductsViewModel - TU PARTE
        if (modelClass.isAssignableFrom(ProductsViewModel::class.java)) {
            return ProductsViewModel(productRepository) as T
        }
        // UserViewModel - ← AGREGAR ESTE BLOQUE
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(userRepository, sessionManager) as T
        }
        // ProductsViewModel
        if (modelClass.isAssignableFrom(ProductsViewModel::class.java)) {
            return ProductsViewModel(productRepository) as T
        }

        throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
    }
}