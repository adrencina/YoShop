package com.example.claraterra.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.claraterra.ui.screen.balance.BalanceScreen
import com.example.claraterra.ui.screen.home.HomeScreen
import com.example.claraterra.ui.screen.inventory.InventoryScreen
import com.example.claraterra.ui.screen.login.LoginScreen
import com.example.claraterra.ui.screen.photo.PhotoScreen
import com.example.claraterra.ui.screen.settings.SettingsScreen
import com.example.claraterra.ui.screen.splash.SplashScreen

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

        // 🔹 Rutas del BottomNavigationBar
        composable(NavigationRoute.Inventory.route) {
            InventoryScreen(navController = navController)
        }

        composable(NavigationRoute.Photo.route) {
            PhotoScreen(navController = navController)
        }

        composable(NavigationRoute.Balance.route) {
            BalanceScreen(navController = navController)
        }

        composable(NavigationRoute.Settings.route) {
            SettingsScreen(navController = navController)
        }
    }
}