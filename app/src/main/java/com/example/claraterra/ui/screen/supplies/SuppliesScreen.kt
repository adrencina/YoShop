package com.example.claraterra.ui.screen.supplies

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.navigation.NavController
import com.example.claraterra.ui.component.BottomNavigationBar
import com.example.claraterra.ui.component.ScreenContainer

@Composable
fun SuppliesScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { innerPadding ->

        ScreenContainer(modifier = Modifier.padding(innerPadding)) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Pantalla de Proveedor")
            }
        }
    }
}