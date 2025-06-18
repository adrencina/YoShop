package com.adrencina.yoshop.ui.screen.home.sections

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.adrencina.yoshop.ui.component.cardGradientBackground
import com.adrencina.yoshop.ui.component.LinearProgressBarWithValue
import com.adrencina.yoshop.ui.component.SemiCircleChart
import com.adrencina.yoshop.ui.navigation.NavigationRoute
import com.adrencina.yoshop.ui.screen.home.sections.component.ActionCard
import com.adrencina.yoshop.ui.screen.home.state.HomeUiState
import java.text.NumberFormat
import java.util.Locale

@Composable
fun GraphSection(
    modifier: Modifier = Modifier,
    navController: NavController,
    uiState: HomeUiState
) {
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("es", "AR"))

    Row(
        modifier = modifier.fillMaxWidth().height(240.dp),
    ) {
        Card(
            modifier = Modifier.fillMaxHeight().weight(3f).padding(end = 8.dp),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize().cardGradientBackground().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Ganancia del día",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )

                SemiCircleChart(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    value = uiState.gananciaNetaDiaria.toFloat(),
                    maxValue = 10000f,
                    primaryColor = if (uiState.gananciaNetaDiaria >= 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                    secondaryColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                    centerText = currencyFormatter.format(uiState.gananciaNetaDiaria),
                    strokeWidth = 40.dp
                )

                Spacer(Modifier.weight(1f))

                LinearProgressBarWithValue(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.ingresoBrutoSemanal.toFloat(),
                    maxValue = uiState.metaVentaSemanal.toFloat(),
                    primaryColor = MaterialTheme.colorScheme.secondary,
                    secondaryColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                )

                Text(
                    text = "Objetivo Ingreso Semanal",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxHeight().weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ActionCard(
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.primary,
                icon = Icons.Filled.Add,
                contentDescription = "Nueva Venta",
                onClick = { navController.navigate(NavigationRoute.Sell.route) }
            )
            ActionCard(
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.secondary,
                icon = Icons.Filled.BarChart,
                contentDescription = "Balance",
                onClick = { /* ... */ }
            )
            ActionCard(
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.tertiary,
                icon = Icons.Filled.Storefront,
                contentDescription = "Insumos",
                onClick = { navController.navigate(NavigationRoute.Supplies.route) }
            )
        }
    }
}