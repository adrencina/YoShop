package com.example.claraterra.ui.screen.home.sections

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MotivationalSection(
    modifier: Modifier = Modifier
) {

    // componente de frase

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp)
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Acá van las frases")
            IconButton(
                onClick = { /* ... */ },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
            ) {
                Icon(Icons.Filled.Refresh, contentDescription = "Refresh")
            }
        }
    }
}
