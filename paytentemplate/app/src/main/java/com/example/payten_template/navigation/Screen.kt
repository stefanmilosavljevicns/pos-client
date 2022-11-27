package com.example.payten_template.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object LoginScreen : Screen("login_screen")
    object HomeScreen : Screen("home_screen")
}