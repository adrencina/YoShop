package com.example.claraterra.ui

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.claraterra.ui.navigation.AppNavGraph
import com.example.claraterra.ui.theme.ClaraTerraTheme
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ClaraTerraApp(
    auth: FirebaseAuth = FirebaseAuth.getInstance()
) {
    val navController = rememberNavController()
    val isLoggedIn = auth.currentUser != null

    ClaraTerraTheme {
        Surface {
            AppNavGraph(navController = navController, isLoggedIn = isLoggedIn)
        }
    }
}