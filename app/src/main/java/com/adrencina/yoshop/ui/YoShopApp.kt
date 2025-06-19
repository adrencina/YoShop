package com.adrencina.yoshop.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adrencina.yoshop.ui.common.BottomNavigationBar
import com.adrencina.yoshop.ui.navigation.AppNavGraph
import com.adrencina.yoshop.ui.navigation.NavigationRoute
import com.adrencina.yoshop.ui.login.LoginScreen
import com.adrencina.yoshop.ui.register.RegisterScreen
import com.adrencina.yoshop.ui.settings.SettingsRepository
import com.adrencina.yoshop.ui.splash.SplashScreen
import com.adrencina.yoshop.ui.theme.ClaraTerraTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    settingsRepository: SettingsRepository
): ViewModel() {
    val isDarkTheme = settingsRepository.isDarkThemeFlow
}

@Composable
fun YoShopApp(
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val isDarkTheme by mainViewModel.isDarkTheme.collectAsStateWithLifecycle(initialValue = false)
    val navController = rememberNavController()

    ClaraTerraTheme(darkTheme = isDarkTheme) {
        Surface {
            NavHost(
                navController = navController,
                startDestination = NavigationRoute.Splash.route
            ) {
                composable(NavigationRoute.Splash.route) {
                    SplashScreen(
                        onNavigateToLogin = {
                            navController.navigate(NavigationRoute.Login.route) {
                                popUpTo(NavigationRoute.Splash.route) { inclusive = true }
                            }
                        },
                        onNavigateToHome = {
                            navController.navigate("main_app_graph") {
                                popUpTo(NavigationRoute.Splash.route) { inclusive = true }
                            }
                        }
                    )
                }
                composable(NavigationRoute.Login.route) {
                    LoginScreen(
                        onNavigateToRegister = { navController.navigate("register") },
                        onLoginSuccess = {
                            navController.navigate("main_app_graph") {
                                popUpTo(NavigationRoute.Login.route) { inclusive = true }
                            }
                        }
                    )
                }
                composable("register") {
                    RegisterScreen(
                        onRegisterSuccess = {
                            navController.navigate("main_app_graph") {
                                popUpTo("register") { inclusive = true }
                            }
                        }
                    )
                }
                composable("main_app_graph") {
                    val mainNavController = rememberNavController()
                    Scaffold(
                        bottomBar = { BottomNavigationBar(navController = mainNavController) }
                    ) { innerPadding ->

                        AppNavGraph(
                            navController = mainNavController,
                            rootNavController = navController,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}