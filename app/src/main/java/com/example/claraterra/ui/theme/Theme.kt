package com.example.claraterra.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
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

private val DarkColors = darkColorScheme(
    primary = TerraOnPrimary,
    secondary = TerraOnSecondary,
    tertiary = TerraOnPrimary,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    error = TerraError,
    onPrimary = TerraPrimary,
    onSecondary = TerraSecondary,
    onBackground = TerraBackground,
    onSurface = TerraBackground,
    onError = TerraOnError
)

@Composable
fun ClaraTerraTheme(
    darkTheme: Boolean = false, // Siempre modo claro por ahora
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    CompositionLocalProvider(
        LocalSpacing provides Spacing(),
    ) {
        MaterialTheme(
            colorScheme = colors,
            typography = AppTypography,
            shapes = AppShapes,
            content = content
        )
    }
}