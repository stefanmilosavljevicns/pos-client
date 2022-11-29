    package com.example.payten_template.navigation

    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.Modifier
    import androidx.navigation.NavHostController
    import androidx.navigation.compose.NavHost
    import androidx.navigation.compose.composable
    import com.example.payten_template.*


    @Composable
    fun SetupNavGraph(navController: NavHostController) {
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route
        ) {
            composable(route = Screen.Splash.route) {
                AnimatedSplashScreen(navController = navController)
            }
            composable(route = Screen.LoginScreen.route) {
                LoginScreen(navController = navController)
            }
            composable(route = Screen.HomeScreen.route) {
                HomeScreen(navController = navController)
            }
            composable(route = Screen.Rezervacije.route) {
                Rezervacije(navController = navController)
            }
            composable(route = Screen.Rezervisi.route) {
                Rezervisi(navController = navController)
            }
            composable(route = Screen.Arhiva.route) {
                Arhiva(navController = navController)
            }
        }}