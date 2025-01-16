package com.example.gourmetglobe.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

// Thème clair
val LightColors = lightColorScheme(
    primary = pummpink,
    onPrimary = late,
    secondary = olive,
    onSecondary = davygray,
    background = cosmiclate,
    onBackground = davygray,
    surface = cosmiclate,
    onSurface = davygray,
    error = lightred,
    onError = cosmiclate
)

// Thème sombre
val DarkColors = darkColorScheme(
    primary = pummpink,
    onPrimary = davygray,
    secondary = olive,
    onSecondary = cosmiclate,
    background = davygray,
    onBackground = late,
    surface = davygray,
    onSurface = silver,
    error = lightred,
    onError = cosmiclate
)

@Composable
fun GourmetGlobeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColors
    } else {
        LightColors
    }

    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(8.dp),
        large = RoundedCornerShape(16.dp)
    )
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = shapes,
        content = content
    )
}