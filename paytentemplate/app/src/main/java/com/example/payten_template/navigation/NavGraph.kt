    package com.example.payten_template.navigation

    import androidx.compose.runtime.Composable
    import androidx.navigation.NavHostController
    import androidx.navigation.NavType
    import androidx.navigation.compose.NavHost
    import androidx.navigation.compose.composable
    import androidx.navigation.navArgument
    import com.example.payten_template.*
    import com.example.payten_template.ui.core.RezervacijaScreen


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
                ArhivaScreen(navController = navController)
            }
            composable(
                "${Screen.Rezervacija.route}/{sId}",
                arguments = listOf(navArgument("sId") {
                    type = NavType.StringType
                })
            ) {
                RezervacijaScreen(
                    navController = navController,
                    id = it.arguments?.getString("sId") ?: ""
                )
            }
        }}