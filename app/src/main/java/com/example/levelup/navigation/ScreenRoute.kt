package com.example.levelup.navigation

sealed class ScreenRoute(val route: String) {
    data object Home : ScreenRoute("home")
    data object Login : ScreenRoute("login")
    data object Register : ScreenRoute("register")
}