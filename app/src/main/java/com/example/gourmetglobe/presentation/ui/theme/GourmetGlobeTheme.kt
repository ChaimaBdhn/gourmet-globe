package com.example.gourmetglobe.presentation.ui.theme


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColorScheme(
    primary = Color(0xdda88a),
    secondary = Color(0x98bc62),
    background = Color(0xfff4e0),
    surface = Color.White,
    error = Color(0xFFB00020),
)

@Composable
fun GourmetGlobeTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorPalette,
        typography = androidx.compose.material3.Typography(),
        content = content
    )
}