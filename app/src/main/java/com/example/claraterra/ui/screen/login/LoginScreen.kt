package com.example.claraterra.ui.screen.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun LoginScreen(
    navController: NavController
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Pantalla de Login", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    // 🔸 Simulación de login exitoso
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            ) {
                Text("Iniciar sesión con Google (simulado)")
            }
        }
    }
}