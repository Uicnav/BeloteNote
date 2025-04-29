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

val EmeraldGreen = Color(0xFF2ECC71)      // Verde smarald (background)
val IvoryWhite = Color(0xFFFFFDF4)         // Alb ivory (surface & text)
val AmberYellow = Color(0xFFFFC107)        // Galben ambră (primary accent)
val Charcoal = Color(0xFF333333)           // Gri închis (text principal)
val SkyBlue = Color(0xFF4FC3F7)            // Albastru cer (secondary accent)
val MintGreen = Color(0xFF98FF98)          // Verde mentă (detalii, highlights)

val DeepGreen = Color(0xFF145A32)          // Verde închis (background dark)
val Graphite = Color(0xFF1C1C1C)           // Gri cărbune (surface)
val GoldYellow = Color(0xFFFFD700)         // Galben auriu (primary accent)
val LightGray = Color(0xFFEEEEEE)          // Gri deschis (text)
val CyanBlue = Color(0xFF00BCD4)           // Cyan deschis (secondary accent)
val TealGreen = Color(0xFF20C997)          // Verde teal (detalii moderne)

private val BeloteLightColors = lightColorScheme(
    primary = AmberYellow,
    onPrimary = Charcoal,
    secondary = SkyBlue,
    onSecondary = Charcoal,
    background = EmeraldGreen,
    onBackground = Charcoal,
    surface = IvoryWhite,
    onSurface = Charcoal
                                                )

private val BeloteDarkColors = darkColorScheme(
    background = DeepGreen,
    onBackground = Color.White,      // TOT TEXTUL PE BACKGROUND ÎNCHIS = ALB

    surface = Color(0xFF3A3A3A),     // CARDURI/DIALOGURI
    onSurface = Color.White,         // TOT TEXTUL PE CARDURI = ALB

    primary = GoldYellow,
    onPrimary = Color.Black,

    secondary = CyanBlue,
    onSecondary = Color.White
                                              )



val BeloteTypography = Typography(
    displayLarge = TextStyle(
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.2.sp,
        color = AmberYellow
                            ),
    titleLarge = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.5.sp,
        color = Charcoal
                          ),
    bodyLarge = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.25.sp,
        color = Charcoal
                         ),
    bodyMedium = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        color = Charcoal
                          ),
    labelLarge = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        color = SkyBlue
                          ),
    labelSmall = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Light,
        color = TealGreen
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
