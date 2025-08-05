package com.ionvaranita.belotenote.utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val PrimaryBlue = Color(0xFF1565C0)      // Modern deep blue
val AccentOrange = Color(0xFFFF9800)     // Vibrant orange accent
val SurfaceGray = Color(0xFFF5F7FA)      // Soft off-white for surfaces
val DarkSurface = Color(0xFF232946)      // Deep blue-gray for dark mode
val TextOnPrimary = Color(0xFFFFFFFF)    // White text on primary
val TextOnBackground = Color(0xFF232946) // Dark text on light bg
val TextOnDark = Color(0xFFF5F7FA)       // Light text on dark bg

private val BeloteLightColors = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = TextOnPrimary,
    secondary = AccentOrange,
    onSecondary = TextOnPrimary,
    background = SurfaceGray,
    onBackground = TextOnBackground,
    surface = Color.White,
    onSurface = TextOnBackground
)

private val BeloteDarkColors = darkColorScheme(
    primary = PrimaryBlue,
    onPrimary = TextOnPrimary,
    secondary = AccentOrange,
    onSecondary = TextOnPrimary,
    background = DarkSurface,
    onBackground = TextOnDark,
    surface = Color(0xFF1A1A2E),
    onSurface = TextOnDark
)

val BeloteTypography = Typography(
    displayLarge = TextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
    ),
    titleLarge = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.Medium
    ),
    bodyLarge = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal
    ),
    bodyMedium = TextStyle(
        fontSize = 16.sp
    ),
    labelLarge = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold
    ),
    labelSmall = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal
    )
)

@Composable
fun BeloteTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
               ) {
    val colors = if (darkTheme) BeloteDarkColors else BeloteLightColors

    MaterialTheme(
        colorScheme = colors,
        typography = BeloteTypography,
        shapes = Shapes(
            small = RoundedCornerShape(8.dp),
            medium = RoundedCornerShape(16.dp),
            large = RoundedCornerShape(24.dp)
                       ),
        content = content
                 )
}
