package com.example.claraterra.ui.navigation

sealed class NavigationRoute(val route: String) {

    object Splash      : NavigationRoute("splash")
    object Login       : NavigationRoute("login")

    object Home      : NavigationRoute("home")
    object Supplies : NavigationRoute("supplies")
    object Sell     : NavigationRoute("sell")
    object Balance   : NavigationRoute("balance")
    object Settings  : NavigationRoute("settings")
}