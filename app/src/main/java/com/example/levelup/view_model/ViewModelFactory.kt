package com.example.levelup.view_model

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.levelup.navigation.NavRouter
import com.example.levelup.repository.ApplicationDatabase
import com.example.levelup.repository.UserRepository
import com.example.levelup.ui.theme.TestWithDBTheme
import com.example.levelup.view_model.ViewModelFactory
import androidx.lifecycle.ViewModelProvider


class ViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Si me piden LoginViewModel, lo creo
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(userRepository) as T
        }
        // Si me piden RegisterViewModel, lo creo
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(userRepository) as T
        }
        // Si piden otro, error
        throw IllegalArgumentException("ViewModel desconocido")
    }
}