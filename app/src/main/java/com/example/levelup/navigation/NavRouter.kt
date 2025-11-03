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
import com.example.levelup.view_model.ProductsViewModel

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

            // Profile
            composable(ScreenRoute.Profile.route) {
                ProfileScreen(
                    viewModel = viewModel(factory = viewModelFactory),
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onLogout = {
                        navController.navigate(ScreenRoute.Home.route) {
                            popUpTo(ScreenRoute.Home.route) { inclusive = true }
                        }
                    }
                )
            }

            // Products - CATÁLOGO
            composable(ScreenRoute.Products.route) {
                val productsViewModel: ProductsViewModel = viewModel(factory = viewModelFactory)
                ProductsScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onProductClick = { productId ->
                        navController.navigate(ScreenRoute.ProductDetail.createRoute(productId))
                    },
                    viewModel = productsViewModel
                )
            }

            // ProductDetail - DETALLE DEL PRODUCTO
            composable(
                route = ScreenRoute.ProductDetail.route,
                arguments = listOf(navArgument("productId") { type = NavType.IntType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getInt("productId") ?: 0
                val productsViewModel: ProductsViewModel = viewModel(factory = viewModelFactory)
                ProductDetailScreen(
                    productId = productId,
                    onNavigateBack = { navController.popBackStack() },
                    viewModel = productsViewModel
                )
            }
        }
    }
}