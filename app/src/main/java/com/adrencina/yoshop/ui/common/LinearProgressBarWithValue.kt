package com.adrencina.yoshop.ui.common
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LinearProgressBarWithValue(
    modifier: Modifier = Modifier,
    value: Float,
    maxValue: Float,
    primaryColor: Color,
    secondaryColor: Color
) {
    val progress = (value / maxValue).coerceIn(0f, 1f)
    val percentageText = "${(progress * 100).toInt()}%"

    Box(
        modifier = modifier
            .height(28.dp)
            .background(secondaryColor),
        contentAlignment = Alignment.CenterStart
    ) {
        // Barra de progreso (la que se llena)
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(fraction = progress)
                .background(primaryColor)
        )

        Text(
            text = percentageText,
            modifier = Modifier.padding(start = 12.dp),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}