package com.adrencina.yoshop.ui.screen.supplies

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.adrencina.yoshop.ui.component.GreetingTopBar

@Composable
fun SuppliesScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        GreetingTopBar(userName = "tus Insumos")

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Proximamente: Pantalla de Proveedores/Insumos")
        }
    }
}