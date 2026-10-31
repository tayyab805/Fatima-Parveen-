package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Regal Gold Palette
val GoldPrimary = Color(0xFFB8860B)        // Dark Goldenrod / Classic Rich Gold
val GoldSecondary = Color(0xFFD4AF37)      // Metallic Gold
val GoldTertiary = Color(0xFFE5C158)       // Luminous Light Gold
val GoldAccent = Color(0xFFFFD700)         // Pure Gold Glow
val GoldDark = Color(0xFF75550C)           // Royal Deep Bronze Gold
val GoldContainer = Color(0xFFFCF7E8)      // Shimmering Ivory Gold
val OnGoldContainer = Color(0xFF4A3602)    // Dark Gold Contrast
val GoldBorder = Color(0xFFDFC68A)         // Elegant Gold Border
val GoldDivider = Color(0xFFEFE2BC)

// White & Ivory Palette
val PureWhite = Color(0xFFFFFFFF)
val PearlBackground = Color(0xFFFAF8F5)    // Regal Pearl Ivory Background
val PearlCard = Color(0xFFFFFFFF)          // Crisp White Card
val PearlSurface = Color(0xFFF5EFE3)       // Soft Golden Tinted Surface
val PearlMuted = Color(0xFFF0E9DA)

// High-contrast legible text
val CharcoalText = Color(0xFF231F1A)       // Crisp readable body text
val CharcoalMuted = Color(0xFF6E6557)      // Secondary label text
val CharcoalFaint = Color(0xFF9E9585)

// Luxury Gradients
val GoldShimmerBrush = Brush.linearGradient(
    colors = listOf(
        Color(0xFFEED07C),
        Color(0xFFC99728),
        Color(0xFFFFDF88),
        Color(0xFFB8860B)
    )
)

val GoldCardGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFFFFFFFF),
        Color(0xFFFCF9F1)
    )
)

val GoldHeaderBrush = Brush.horizontalGradient(
    colors = listOf(
        Color(0xFF6B4E03),
        Color(0xFF9E7711),
        Color(0xFFB8860B)
    )
)

// Dark Luxury Mode
val ObsidianBackground = Color(0xFF14120E)
val ObsidianSurface = Color(0xFF1E1A14)
val ObsidianCard = Color(0xFF262118)
val ObsidianBorder = Color(0xFF5E4919)
val GoldTextLight = Color(0xFFF9E8B2)
