package com.example.claraterra.ui.screen.balance.sections.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun ChartPie(
    modifier: Modifier = Modifier,
    porcentajes: Array<Float>
)
{
    val anguloinicial = -90f
    var anguloActual = anguloinicial
    var anguloFinal = 0f
    var total = porcentajes.sum()

    Box{
        porcentajes.forEach { element ->
            Canvas(modifier = Modifier
                .size(200.dp)){
                val centerX = size.width / 2
                val centerY = size.height / 2

                anguloFinal = (element / total) * 360
                val midAngle = anguloActual * anguloFinal / 2
                drawArc(
                    color = generateRandomColor(),
                    startAngle = anguloActual,
                    sweepAngle = anguloFinal,
                    useCenter = true,
                    style = Fill
                )

                anguloActual += anguloFinal

                //Calculo de coordenadas para el texto
                val textX = centerX + ((size.width) / 3) * cos(Math.toRadians(midAngle.toDouble()).toFloat())
                val textY = centerY + ((size.height) / 3) * sin(Math.toRadians(midAngle.toDouble()).toFloat())

                //dibuja el texto con el porcentaje
                val porcentaje = (element / total * 100).toInt()
                val text = "$porcentaje"
                val textPaint = Paint().asFrameworkPaint()
                textPaint.color = Color.Black.toArgb()
                textPaint.textSize = 55f
                textPaint.isFakeBoldText = true
                drawContext.canvas.nativeCanvas.drawText(text, textX, textY, textPaint)


            }
        }
    }
}

fun generateRandomColor():Color{
    val random = Random.Default
    val red = random.nextInt(256)
    val green = random.nextInt(256)
    val blue = random.nextInt(256)
    return Color(red,green, blue)
}