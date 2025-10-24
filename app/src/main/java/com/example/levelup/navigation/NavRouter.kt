package com.example.levelup.navigation

import android.widget.GridView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.levelup.ui.screens.HomeScreen
import com.example.levelup.ui.screens.LoginScreen
import com.example.levelup.ui.screens.RegisterScreen
import com.example.levelup.view_model.ViewModelFactory

@Composable
fun NavRouter(
    navController: NavHostController,
    innerPadding: PaddingValues,
    viewModelFactory: ViewModelFactory
) {
    Column(modifier = Modifier.padding(innerPadding)) {
        NavHost(
            navController = navController,
            startDestination = ScreenRoute.Home.route
        ) {
            composable(ScreenRoute.Home.route) {
                HomeScreen(
                    onNavigateToLogin = {
                        navController.navigate(ScreenRoute.Login.route)
                    },
                    onNavigateToRegister = {
                        navController.navigate(ScreenRoute.Register.route)
                    }
                )
            }

            composable(ScreenRoute.Login.route) {
                LoginScreen(
                    onNavigateToHome = {
                        navController.navigate(ScreenRoute.Home.route) {
                            popUpTo(ScreenRoute.Home.route) { inclusive = true }
                        }
                    },
                    onNavigateToRegister = {
                        navController.navigate(ScreenRoute.Register.route)
                    },
                    onNavigateBack = {
                        navController.navigate(ScreenRoute.Home.route)
                    },
                    viewModel = viewModel(factory = viewModelFactory)
                )
            }

            composable(ScreenRoute.Register.route) {
                RegisterScreen(
                    onNavigateToHome = {
                        navController.navigate(ScreenRoute.Home.route) {
                            popUpTo(ScreenRoute.Home.route) { inclusive = true }
                        }
                    },
                    onNavigateToLogin = {
                        navController.navigate(ScreenRoute.Login.route)
                    },
                    onNavigateBack = {
                        navController.navigate(ScreenRoute.Home.route)
                    },
                    viewModel = viewModel(factory = viewModelFactory)
                )
            }
        }
    }
}