package com.example.levelup.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.levelup.repository.ApplicationDatabase
import com.example.levelup.repository.UserRepository
import com.example.levelup.repository.CartRepository
import com.example.levelup.repository.ProductRepository
import com.example.levelup.utils.SessionManager

class ViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val database = ApplicationDatabase.getDatabase(application)
        val sessionManager = SessionManager(application)

        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                val userRepository = UserRepository(database.userDao())
                LoginViewModel(userRepository, sessionManager) as T
            }

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                val userRepository = UserRepository(database.userDao())
                RegisterViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(ProductsViewModel::class.java) -> {
                val productRepository = ProductRepository(database.productDao())
                ProductsViewModel(productRepository) as T
            }

            modelClass.isAssignableFrom(CartViewModel::class.java) -> {
                val cartRepository = CartRepository(database.cartDao())
                val currentUserId = sessionManager.getUserId().toString()
                CartViewModel(cartRepository, currentUserId) as T
            }

            modelClass.isAssignableFrom(UserViewModel::class.java) -> {
                val userRepository = UserRepository(database.userDao())
                UserViewModel(userRepository, sessionManager) as T
            }

            modelClass.isAssignableFrom(FactViewModel::class.java) -> {
                FactViewModel() as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}