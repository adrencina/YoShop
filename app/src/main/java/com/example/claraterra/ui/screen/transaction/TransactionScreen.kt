package com.example.claraterra.ui.screen.transaction

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.navigation.NavController
import com.example.claraterra.ui.component.BottomNavigationBar

@Composable
fun TransactionScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Esta es la pantalla de Ventas / Transacciones")
        }
    }
}