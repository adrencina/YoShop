package com.example.claraterra.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun DonutChart(
    sales: Float,
    purchases: Float,
    modifier: Modifier = Modifier,
    salesColor: Color = Color(0xFF81C784),
    purchasesColor: Color = Color(0xFFE57373)
) {
    val total = sales + purchases
    if (total == 0f) return

    val salesAngle = (sales / total) * 180f
    val purchasesAngle = 180f - salesAngle

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val diameter = width.coerceAtMost(height * 3)

        val arcSize = Size(diameter, diameter)
        val stroke = Stroke(width = diameter * 0.25f)

        val topLeft = Offset(
            (width - diameter) / 2f,
            (height - diameter)
        )

        // Dibujar compras (parte izquierda del semicírculo)
        drawArc(
            color = purchasesColor,
            startAngle = 180f,
            sweepAngle = purchasesAngle,
            useCenter = false,
            style = stroke,
            size = arcSize,
            topLeft = topLeft
        )

        // Dibujar ventas (parte derecha del semicírculo)
        drawArc(
            color = salesColor,
            startAngle = 180f + purchasesAngle,
            sweepAngle = salesAngle,
            useCenter = false,
            style = stroke,
            size = arcSize,
            topLeft = topLeft
        )
    }
}