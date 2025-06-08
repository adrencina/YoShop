package com.example.claraterra.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = TerraPrimary,
    secondary = TerraSecondary,
    tertiary = TerraTertiary,
    background = TerraBackground,
    surface = TerraSurface,
    error = TerraError,
    onPrimary = TerraOnPrimary,
    onSecondary = TerraOnSecondary,
    onBackground = TerraOnBackground,
    onSurface = TerraOnSurface,
    onError = TerraOnError
)

@Composable
fun ClaraTerraTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}