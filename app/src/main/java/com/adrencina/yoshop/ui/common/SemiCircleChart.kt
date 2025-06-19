package com.adrencina.yoshop.ui.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SemiCircleChart(
    modifier: Modifier = Modifier,
    value: Float,
    maxValue: Float,
    primaryColor: Color,
    secondaryColor: Color,
    centerText: String,
    strokeWidth: Dp = 50.dp
) {
    val strokeWidthPx = with(LocalDensity.current) { strokeWidth.toPx() }
    val sweepAngle = 180 * (value / maxValue).coerceIn(0f, 1f)

    Box(
        modifier = modifier.aspectRatio(2f),
        contentAlignment = Alignment.BottomCenter
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val outerDiameter = size.width
            val arcDiameter = outerDiameter - strokeWidthPx
            val arcTopLeftOffset = strokeWidthPx / 2
            val arcSize = Size(arcDiameter, arcDiameter)

            // Arco de fondo
            drawArc(
                color = secondaryColor,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = Offset(x = arcTopLeftOffset, y = arcTopLeftOffset),
                size = arcSize,
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Butt)
            )

            // Arco de progreso
            drawArc(
                color = primaryColor,
                startAngle = 180f,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = Offset(x = arcTopLeftOffset, y = arcTopLeftOffset),
                size = arcSize,
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Butt)
            )
        }

        Text(
            text = centerText,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}