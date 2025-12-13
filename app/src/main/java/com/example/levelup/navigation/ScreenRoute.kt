package com.example.levelup.navigation

sealed class ScreenRoute(val route: String) {
    object Home : ScreenRoute("home")
    object Index : ScreenRoute("index")
    object Login : ScreenRoute("login")
    object Register : ScreenRoute("register")
    object Profile : ScreenRoute("profile")
    object Products : ScreenRoute("products")
    object ProductDetail : ScreenRoute("product_detail/{productId}") {
        fun createRoute(productId: Int) = "product_detail/$productId"
    }
    object Cart : ScreenRoute("cart")
    object Checkout : ScreenRoute("checkout")
    object OrderConfirmation : ScreenRoute("order_confirmation")
}