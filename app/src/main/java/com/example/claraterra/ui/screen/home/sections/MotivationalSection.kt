package com.example.claraterra.ui.screen.home.sections

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.claraterra.ui.component.cardGradientBackground

@Composable
fun MotivationalSection(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp), // Una altura más contenida y elegante
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        // Usamos el degradado que ya tenías para darle un toque especial y de marca.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .cardGradientBackground(), // ¡Reutilizamos tu componente!
            contentAlignment = Alignment.Center
        ) {
            // La frase motivadora, centrada y con la tipografía de tu app.
            Text(
                text = "Cada producto que creás es un paso más hacia tu sueño.",
                style = MaterialTheme.typography.titleLarge, // Usamos una tipografía con presencia
                fontSize = 20.sp, // Ajustamos el tamaño para que se lea bien
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }
    }
}