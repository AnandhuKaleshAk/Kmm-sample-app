package org.example.project.theme

import DarkColorPalette
import LightColorPalette
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable

@Composable
fun MyAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // This checks the system setting by default
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = CustomTypography(), // Add custom typography if needed
        shapes = Shapes(),         // Add custom shapes if needed
        content = content
    )
}
