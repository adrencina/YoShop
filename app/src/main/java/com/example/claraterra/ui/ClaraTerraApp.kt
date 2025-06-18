package com.example.claraterra.ui

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
import com.example.claraterra.ui.component.BottomNavigationBar
import com.example.claraterra.ui.navigation.AppNavGraph
import com.example.claraterra.ui.navigation.NavigationRoute
import com.example.claraterra.ui.screen.login.LoginScreen
import com.example.claraterra.ui.screen.register.RegisterScreen
import com.example.claraterra.ui.screen.settings.SettingsRepository
import com.example.claraterra.ui.screen.splash.SplashScreen
import com.example.claraterra.ui.theme.ClaraTerraTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    settingsRepository: SettingsRepository
): ViewModel() {
    val isDarkTheme = settingsRepository.isDarkThemeFlow
}

@Composable
fun ClaraTerraApp(
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