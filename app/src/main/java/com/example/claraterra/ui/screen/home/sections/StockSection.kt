package com.example.claraterra.ui.screen.home.sections

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StockSection(
    modifier: Modifier = Modifier
) {
    // Aquí irán tus cards de inventario y barra de stock
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Acá va el stock")
        }
    }
}
