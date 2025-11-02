package com.example.levelup

import android.os.Bundle
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
import com.example.levelup.repository.ProductRepository
import com.example.levelup.ui.theme.TestWithDBTheme
import com.example.levelup.utils.SessionManager
import com.example.levelup.view_model.ViewModelFactory

class MainActivity : ComponentActivity() {
    private lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = ApplicationDatabase.getDatabase(applicationContext)
        val userRepository = UserRepository(database.userDao())
        val productRepository = ProductRepository(database.productDao()) // TU PARTE
        val sessionManager = SessionManager(this)

        viewModelFactory = ViewModelFactory(
            userRepository,
            productRepository,  // TU PARTE
            sessionManager
        )

        setContent {
            TestWithDBTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavRouter(
                        navController = navController,
                        innerPadding = innerPadding,
                        viewModelFactory = viewModelFactory
                    )
                }
            }
        }
    }
}