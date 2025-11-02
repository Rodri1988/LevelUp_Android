package com.example.levelup.navigation

sealed class ScreenRoute(val route: String) {
    object Home : ScreenRoute("home")
    object Index : ScreenRoute("index/{username}") {
        fun createRoute(username: String) = "index/$username"
    }
    object Login : ScreenRoute("login")
    object Register : ScreenRoute("register")
    object Profile : ScreenRoute("profile")
    object Products : ScreenRoute("products")
    object ProductDetail : ScreenRoute("product/{productId}") {
        fun createRoute(productId: Int) = "product/$productId"
    }
}