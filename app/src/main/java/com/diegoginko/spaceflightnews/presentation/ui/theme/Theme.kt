package com.diegoginko.spaceflightnews.presentation.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = SpaceBlueLight80,
    onPrimary = Color.White,
    primaryContainer = SpaceBlueDark,
    onPrimaryContainer = SpaceWhite,
    secondary = SpaceBlueLight80,
    onSecondary = Color.White,
    tertiary = SpaceOrangeLight,
    onTertiary = Color.White,
    background = Color(0xFF0A1929),
    onBackground = SpaceWhite,
    surface = Color(0xFF121212),
    onSurface = SpaceWhite,
    surfaceVariant = Color(0xFF1E1E1E),
    onSurfaceVariant = SpaceGray80,
    error = Color(0xFFBA1A1A),
    onError = Color.White,
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6)
)

private val LightColorScheme = lightColorScheme(
    primary = SpaceBlue,
    onPrimary = Color.White,
    primaryContainer = SpaceBlueLight,
    onPrimaryContainer = SpaceBlueDark,
    secondary = SpaceBlueLight,
    onSecondary = Color.White,
    tertiary = SpaceOrange,
    onTertiary = Color.White,
    background = SpaceWhite,
    onBackground = Color(0xFF1C1B1F),
    surface = Color.White,
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = Color(0xFF49454F),
    error = Color(0xFFBA1A1A),
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002)
)

@Composable
fun SpaceFlightNewsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color deshabilitado para mantener los colores de la marca
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}