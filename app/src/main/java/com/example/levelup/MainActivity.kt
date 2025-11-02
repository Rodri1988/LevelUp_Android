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
import com.example.levelup.ui.theme.TestWithDBTheme
import com.example.levelup.utils.SessionManager  // 🆕 Import
import com.example.levelup.view_model.ViewModelFactory

class MainActivity : ComponentActivity() {
    private lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize database, repository and session manager
        val database = ApplicationDatabase.getDatabase(applicationContext)
        val userRepository = UserRepository(database.userDao())
        val sessionManager = SessionManager(this)  // 🆕 Agregado
        viewModelFactory = ViewModelFactory(userRepository, sessionManager)  // 🆕 Agregado sessionManager

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