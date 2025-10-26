package com.example.levelup.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.levelup.ui.screens.*
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
            // Home
            composable(ScreenRoute.Home.route) {
                HomeScreen(navController = navController)
            }

            // Index con username opcional
            composable(
                route = "${ScreenRoute.Index.route}/{username}",
                arguments = listOf(navArgument("username") { type = NavType.StringType })
            ) { backStackEntry ->
                val username = backStackEntry.arguments?.getString("username") ?: ""
                IndexScreen(navController = navController, username = username)
            }

            // Login
            composable(ScreenRoute.Login.route) {
                LoginScreen(
                    onNavigateToHome = { username ->
                        navController.navigate("${ScreenRoute.Index.route}/$username") {
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

            // Register
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