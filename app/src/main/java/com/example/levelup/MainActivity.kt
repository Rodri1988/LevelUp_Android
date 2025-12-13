package com.example.levelup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.levelup.navigation.NavRouter
import com.example.levelup.ui.theme.TestWithDBTheme  // ✅ Usa el nombre correcto
import com.example.levelup.view_model.ViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelFactory = ViewModelFactory(application)

        setContent {
            TestWithDBTheme {  // ✅ Nombre correcto del tema
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
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