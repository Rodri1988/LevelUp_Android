package com.example.levelup.navigation

sealed class ScreenRoute(val route: String) {
    object Home : ScreenRoute("home")
    object Index : ScreenRoute("index/{username}") {
        fun createRoute(username: String) = "index/$username"
    }
    object Login : ScreenRoute("login")
    object Register : ScreenRoute("register")
}