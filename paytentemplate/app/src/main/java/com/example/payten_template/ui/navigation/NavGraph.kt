    package com.example.payten_template.ui.navigation

    import androidx.compose.runtime.Composable
    import androidx.navigation.NavHostController
    import androidx.navigation.NavType
    import androidx.navigation.compose.NavHost
    import androidx.navigation.compose.composable
    import androidx.navigation.navArgument
    import com.example.payten_template.*
    import com.example.payten_template.ui.core.RezervacijaScreen
    import com.example.payten_template.ui.core.orders.OrdersScreen


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
            composable(route = Screen.ArchivedOrders.route) {
                OrdersScreen(archive = true, navController = navController)
            }
            composable(route = Screen.Orders.route) {
                OrdersScreen(navController = navController)
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