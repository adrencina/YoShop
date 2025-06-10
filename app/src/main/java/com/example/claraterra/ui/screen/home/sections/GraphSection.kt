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
import com.example.claraterra.ui.component.LinearProgressBarWithValue
import com.example.claraterra.ui.component.SemiCircleChart
import com.example.claraterra.ui.navigation.NavigationRoute
import com.example.claraterra.ui.screen.home.sections.component.ActionCard
import com.example.claraterra.ui.theme.AppShapes

@Composable
fun GraphSection(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val dailySales = 400f
    val salesGoal = 1000f
    val weeklyProgress = 3500f
    val weeklyGoal = 5000f

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(240.dp),
    ) {
        // Tarjeta principal del gráfico
        Card(
            modifier = Modifier
                .fillMaxHeight()
                .weight(3f)
                .padding(end = 6.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            shape = AppShapes.small
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título
                Text( modifier = Modifier.padding(bottom = 8.dp),
                    text = "Balance diario",
                    style = MaterialTheme.typography.titleMedium
                )

                // Gráfico semicircular
                SemiCircleChart(
                    modifier = Modifier.fillMaxWidth(),
                    value = dailySales,
                    maxValue = salesGoal,
                    primaryColor = Color(0xFF81C784),
                    secondaryColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                    centerText = "$${dailySales.toInt()}",
                    strokeWidth = 50.dp
                )

                Spacer(Modifier.weight(1f))

                // La barra de progreso
                LinearProgressBarWithValue(
                    modifier = Modifier.fillMaxWidth(),
                    value = weeklyProgress,
                    maxValue = weeklyGoal,
                    primaryColor = Color(0xFF64B5F6),
                    secondaryColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                )

                // Texto inferior
                Text(
                    text = "Objetivo de venta semanal",
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