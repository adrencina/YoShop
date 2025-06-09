package com.example.claraterra.ui.screen.home.sections

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GraphSection(
    modifier: Modifier = Modifier
) {

    // componente de gráfico

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Acá va el gráfico")
        }
    }
}
