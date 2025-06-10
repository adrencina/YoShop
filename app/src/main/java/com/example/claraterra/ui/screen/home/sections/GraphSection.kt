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
import com.example.claraterra.ui.component.DonutChart
import com.example.claraterra.ui.navigation.NavigationRoute
import com.example.claraterra.ui.screen.home.sections.component.ActionCard
import com.example.claraterra.ui.theme.AppShapes

@Composable
fun GraphSection(
    modifier: Modifier = Modifier,
    navController: NavController
) {
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
            colors = CardDefaults.cardColors(containerColor = Color.Gray),
            shape = AppShapes.small
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Título
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Balance diario", style = MaterialTheme.typography.titleMedium)
                }
                // Gráfico circular
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.height(50.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        DonutChart(
                            sales = 400f,
                            purchases = 200f,
                            modifier = Modifier.size(170.dp)
                        )
                    }
                }
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
                onClick = {
                    navController.navigate(NavigationRoute.Sell.route)
                }
            )
            ActionCard(
                modifier = Modifier.weight(1f).padding(vertical = 2.dp),
                color = Color(0xFF64B5F6),
                icon = Icons.Filled.BarChart,
                contentDescription = "View Balance",
                onClick = {
                    // Navegar o vemos...
                }
            )
            ActionCard(
                modifier = Modifier.weight(1f).padding(top = 4.dp),
                color = Color(0xFFE57373),
                icon = Icons.Filled.Storefront,
                contentDescription = "Supplies",
                onClick = {
                    navController.navigate(NavigationRoute.Supplies.route)
                }
            )
        }
    }
}