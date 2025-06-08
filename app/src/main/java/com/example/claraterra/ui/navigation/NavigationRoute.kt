package com.example.claraterra.ui.navigation

sealed class NavigationRoute(val route: String) {

    object Splash      : NavigationRoute("splash")
    object Login       : NavigationRoute("login")

    object Home      : NavigationRoute("home")
    object Inventory : NavigationRoute("inventory")
    object Photo     : NavigationRoute("photo")
    object Balance   : NavigationRoute("balance")
    object Settings  : NavigationRoute("settings")
}