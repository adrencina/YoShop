package com.adrencina.yoshop.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.sp
import com.adrencina.yoshop.R

val Playfair = FontFamily(
    Font(R.font.playfairdisplay_regular, FontWeight.Normal),
    Font(R.font.playfairdisplay_bold, FontWeight.Bold)
)

val Manrope = FontFamily(
    Font(R.font.manrope_regular, FontWeight.Normal),
    Font(R.font.manrope_medium, FontWeight.Medium),
    Font(R.font.manrope_bold, FontWeight.Bold)
)

val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = Playfair,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Manrope,
        fontSize = 16.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Manrope,
        fontSize = 14.sp
    )
)