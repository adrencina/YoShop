package com.example.claraterra.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.claraterra.ui.screen.balance.screen.BalanceScreen
import com.example.claraterra.ui.screen.home.screen.HomeScreen
import com.example.claraterra.ui.screen.login.LoginScreen
import com.example.claraterra.ui.screen.products.ProductManagementScreen
import com.example.claraterra.ui.screen.sell.SellScreen
import com.example.claraterra.ui.screen.settings.SettingsScreen
import com.example.claraterra.ui.screen.splash.SplashScreen
import com.example.claraterra.ui.screen.supplies.SuppliesScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    isLoggedIn: Boolean // Por ahora simulamos en SplashScreen
) {
    val start = if (isLoggedIn) NavigationRoute.Home.route else NavigationRoute.Splash.route

    NavHost(
        navController = navController,
        startDestination = start
    ) {
        // 🔹 Splash
        composable(NavigationRoute.Splash.route) {
            SplashScreen(navController = navController)
        }

        // 🔹 Login
        composable(NavigationRoute.Login.route) {
            LoginScreen(navController = navController)
        }

        // 🔹 Home principal
        composable(NavigationRoute.Home.route) {
            HomeScreen(navController = navController)
        }

        // 🔹 Gestión de productos
        composable(NavigationRoute.Products.route) {
            ProductManagementScreen(/* navController = navController */)
        }

        // 🔹 Rutas del BottomNavigationBar
        composable(NavigationRoute.Supplies.route) {
            SuppliesScreen(navController = navController)
        }

        composable(NavigationRoute.Sell.route) {
            SellScreen(navController = navController)
        }

        composable(NavigationRoute.Balance.route) {
            BalanceScreen(navController = navController)
        }

        composable(NavigationRoute.Settings.route) {
            SettingsScreen(navController = navController)
        }
    }
}