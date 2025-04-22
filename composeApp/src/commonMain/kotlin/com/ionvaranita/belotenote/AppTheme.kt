/*
package com.ionvaranita.belotenote.ui.com.ionvaranita.belotenote

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Light Theme Colors
val md_theme_light_primary = Color(0xFF4CAF50) // Vibrant Green
val md_theme_light_onPrimary = Color(0xFFFFFFFF) // White text
val md_theme_light_primaryContainer = Color(0xFF5d986c) // Deep Emerald Green (For App Bar)
val md_theme_light_onPrimaryContainer = Color(0xFFFFFFFF) // White text
val md_theme_light_background = Color(0xFFF1F8E9) // Soft Greenish White
val md_theme_light_onBackground = Color(0xFF1B1B1B) // Almost Black
val md_theme_light_surface = Color(0xFFE8F5E9) // Light Green Surface
val md_theme_light_onSurface = Color(0xFF2E7D32) // Deep Green Text

// Dark Theme Colors
val md_theme_dark_primary = Color(0xFF81C784) // Soft Green
val md_theme_dark_onPrimary = Color(0xFF00251A) // Deep Forest Green
val md_theme_dark_primaryContainer = Color(0xFF95d986c) // Darker Green (For App Bar)
val md_theme_dark_onPrimaryContainer = Color(0xFFFFFFFF) // White text
val md_theme_dark_background = Color(0xFF121212) // Deep Black-Green
val md_theme_dark_onBackground = Color(0xFFE0E0E0) // Light Gray
val md_theme_dark_surface = Color(0xFF263238) // Dark Greenish-Blue Surface
val md_theme_dark_onSurface = Color(0xFFFFFFFF) // White Text

// Define Light and Dark Color Schemes
private val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer, // Green App Bar
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface
                                               )

private val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer, // Dark Green App Bar
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface
                                             )

// Theme Composable
@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
            ) {
    val colorScheme = if (!darkTheme) {
        LightColorScheme
    } else {
        DarkColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
                 )
}
*/
