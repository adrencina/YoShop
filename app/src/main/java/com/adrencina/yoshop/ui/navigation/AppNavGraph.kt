package com.adrencina.yoshop.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.adrencina.yoshop.ui.screen.balance.screen.BalanceScreen
import com.adrencina.yoshop.ui.screen.home.screen.HomeScreen
import com.adrencina.yoshop.ui.screen.products.ProductManagementScreen
import com.adrencina.yoshop.ui.screen.sell.SellScreen
import com.adrencina.yoshop.ui.screen.settings.SettingsScreen
import com.adrencina.yoshop.ui.screen.supplies.SuppliesScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    rootNavController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.Home.route,
        modifier = modifier
    ) {
        // --- TUS PANTALLAS INTERNAS ---
        composable(NavigationRoute.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(NavigationRoute.Products.route) {
            ProductManagementScreen()
        }
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
            SettingsScreen(
                navController = navController,
                onSignOut = {
                    rootNavController.navigate(NavigationRoute.Login.route) {
                        popUpTo("main_app_graph") { inclusive = true }
                    }
                }
            )
        }
    }
}