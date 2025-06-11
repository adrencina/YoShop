package com.example.claraterra.ui.screen.home.sections

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.claraterra.ui.component.cardGradientBackground
import com.example.claraterra.ui.component.LinearProgressBarWithValue
import com.example.claraterra.ui.component.SemiCircleChart
import com.example.claraterra.ui.navigation.NavigationRoute
import com.example.claraterra.ui.screen.home.sections.component.ActionCard
import com.example.claraterra.ui.screen.home.state.HomeUiState
import com.example.claraterra.ui.theme.AppShapes
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
            modifier = Modifier.fillMaxHeight().weight(3f).padding(end = 6.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            shape = AppShapes.small,
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize().cardGradientBackground().padding(vertical = 16.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = "Ganancia del día",
                    color = Color.Black.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.titleMedium
                )

                // El gráfico de semicírculo muestra la GANANCIA NETA del día.
                SemiCircleChart(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.gananciaNetaDiaria.toFloat(),
                    maxValue = 10000f,
                    primaryColor = if (uiState.gananciaNetaDiaria >= 0) Color(0xFF81C784) else Color(0xFFE57373),
                    secondaryColor = Color.Black.copy(alpha = 0.1f),
                    centerText = currencyFormatter.format(uiState.gananciaNetaDiaria),
                    strokeWidth = 50.dp
                )

                Spacer(Modifier.weight(1f))

                // La barra de progreso muestra el avance hacia la meta SEMANAL de INGRESO BRUTO.
                LinearProgressBarWithValue(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.ingresoBrutoSemanal.toFloat(),
                    maxValue = uiState.metaVentaSemanal.toFloat(),
                    primaryColor = Color(0xFF64B5F6),
                    secondaryColor = Color.Black.copy(alpha = 0.1f)
                )

                Text(
                    text = "Objetivo de Ingreso Semanal",
                    color = Color.Black.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        // Columna de acciones rápidas
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ActionCard(
                modifier = Modifier.weight(1f).padding(bottom = 4.dp),
                color = Color(0xFF81C784),
                icon = Icons.Filled.Add,
                contentDescription = "New Sale",
                onClick = { navController.navigate(NavigationRoute.Sell.route) }
            )
            ActionCard(
                modifier = Modifier.weight(1f).padding(vertical = 2.dp),
                color = Color(0xFF64B5F6),
                icon = Icons.Filled.BarChart,
                contentDescription = "View Balance",
                onClick = { /* Navegar o vemos despues... */ }
            )
            ActionCard(
                modifier = Modifier.weight(1f).padding(top = 4.dp),
                color = Color(0xFFE57373),
                icon = Icons.Filled.Storefront,
                contentDescription = "Supplies",
                onClick = { navController.navigate(NavigationRoute.Supplies.route) }
            )
        }
    }
}