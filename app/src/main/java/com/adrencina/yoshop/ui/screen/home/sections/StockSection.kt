package com.adrencina.yoshop.ui.screen.home.sections

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.adrencina.yoshop.ui.navigation.NavigationRoute
import com.adrencina.yoshop.ui.screen.home.state.HomeUiState
import com.adrencina.yoshop.ui.screen.home.state.StockStatus

@Composable
fun StockSection(
    modifier: Modifier = Modifier,
    navController: NavController,
    uiState: HomeUiState
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 28.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(start = 30.dp),
                    text = "Estado del Inventario",
                    style = MaterialTheme.typography.titleMedium,
                )
                IconButton(onClick = { navController.navigate(NavigationRoute.Products.route) }) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Gestionar productos",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            StatusMessage(uiState = uiState)
        }
    }
}

@Composable
private fun StatusMessage(uiState: HomeUiState) {
    val statusData = when (uiState.stockStatus) {
        StockStatus.EMPTY -> Triple(
            Icons.Default.Info,
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            "No tenés productos en tu inventario."
        )
        StockStatus.OK -> Triple(
            Icons.Default.CheckCircle,
            MaterialTheme.colorScheme.primary,
            "¡Todo tu stock está en orden!"
        )
        StockStatus.LOW -> Triple(
            Icons.Default.Warning,
            MaterialTheme.colorScheme.secondary,
            "Tenés ${uiState.itemsConStockCritico} productos con stock bajo."
        )
        StockStatus.CRITICAL -> Triple(
            Icons.Default.Error,
            MaterialTheme.colorScheme.error,
            "¡Ojo! Tenés ${uiState.itemsConStockCritico} productos Sin stock."
        )
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = statusData.first,
            contentDescription = "Estado del stock",
            tint = statusData.second,
            modifier = Modifier.size(32.dp)
        )
        Text(
            text = statusData.third,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = statusData.second,
            modifier = Modifier.padding(start = 12.dp, bottom = 12.dp)
        )
    }
}