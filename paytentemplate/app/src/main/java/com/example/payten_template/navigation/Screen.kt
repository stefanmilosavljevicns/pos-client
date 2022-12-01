package com.example.payten_template.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object LoginScreen : Screen("login_screen")
    object HomeScreen : Screen("home_screen")
    object Rezervacije : Screen("rezervacije_screen")
    object Rezervisi : Screen("rezervisi_screen")
    object Arhiva : Screen("arhiva_screen")
    object Rezervacija : Screen("rezervacija_screen")
}