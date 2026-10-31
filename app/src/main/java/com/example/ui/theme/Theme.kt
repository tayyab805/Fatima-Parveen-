package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = GoldTertiary,
    onPrimary = ObsidianBackground,
    primaryContainer = ObsidianBorder,
    onPrimaryContainer = GoldAccent,
    secondary = GoldSecondary,
    onSecondary = ObsidianBackground,
    tertiary = GoldAccent,
    background = ObsidianBackground,
    onBackground = GoldTextLight,
    surface = ObsidianSurface,
    onSurface = GoldTextLight,
    surfaceVariant = ObsidianCard,
    onSurfaceVariant = GoldTertiary,
    outline = ObsidianBorder
)

private val LightColorScheme = lightColorScheme(
    primary = GoldPrimary,
    onPrimary = PureWhite,
    primaryContainer = GoldContainer,
    onPrimaryContainer = OnGoldContainer,
    secondary = GoldSecondary,
    onSecondary = PureWhite,
    tertiary = GoldDark,
    background = PearlBackground,
    onBackground = CharcoalText,
    surface = PearlCard,
    onSurface = CharcoalText,
    surfaceVariant = PearlSurface,
    onSurfaceVariant = CharcoalMuted,
    outline = GoldBorder,
    outlineVariant = GoldDivider
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Preserve custom luxury Gold & White branding by default
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
