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

val DarkGreen = Color(0xFF1B4332)      // Fundal principal
val LightIvory = Color(0xFFFFFAF0)     // Text principal pe fundal închis
val Mustard = Color(0xFFFFC107)        // Butoane & accente
val CardSurface = Color(0xFF2D6A4F)    // Dialoguri/Carduri
val ButtonBlue = Color(0xFF00B4D8)     // Accente secundare
val CleanWhite = Color(0xFFFFFFFF)     // Text pe butoane
val CleanBlack = Color(0xFF1C1C1C)     // Text pe fundal deschis
val LightGray = Color(0xFFF2F2F2)      // Fundal general pentru dark mode

private val BeloteLightColors = lightColorScheme(
    primary = Mustard,
    onPrimary = CleanBlack,
    secondary = ButtonBlue,
    onSecondary = CleanBlack,
    background = Color(0xFFE8F5E9),      // Pale green
    onBackground = CleanBlack,
    surface = CleanWhite,
    onSurface = CleanBlack
)

private val BeloteDarkColors = darkColorScheme(
    primary = Mustard,
    onPrimary = CleanBlack,
    secondary = ButtonBlue,
    onSecondary = CleanWhite,
    background = DarkGreen,
    onBackground = LightIvory,
    surface = CardSurface,
    onSurface = LightIvory
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
