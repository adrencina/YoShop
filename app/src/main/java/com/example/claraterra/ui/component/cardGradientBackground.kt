package com.example.claraterra.ui.component

import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier

fun Modifier.cardGradientBackground(): Modifier {
    return this.background(
        brush = Brush.verticalGradient(
            colors = listOf(
                Color(0xFFF4F0FF), // Parte inferior (más claro) 0xFFF4F0FF
                Color(0xFFD7CCFF)  // Parte superior (más oscuro) 0xFFD7CCFF
            )
        )
    )
}