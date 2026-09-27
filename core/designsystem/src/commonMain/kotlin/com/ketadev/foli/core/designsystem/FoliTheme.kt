package com.ketadev.foli.core.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

private val LocalFoliColors = staticCompositionLocalOf { FoliColors.Day }

/**
 * Foli's Material 3 theme, built from the compiled tokens in [FoliColors],
 * [FoliSpacing], [FoliRadius], [FoliSize], and [FoliTypography]. All values are
 * hand-mapped from `design/tokens/design-tokens.json` at compile time; no JSON
 * file is read at runtime.
 *
 * Wrap the app content once, near the root:
 * ```
 * FoliTheme {
 *     // starter shell content
 * }
 * ```
 *
 * Foli-specific tokens beyond the standard Material 3 [MaterialTheme.colorScheme]
 * slots (e.g. `leaf400`, `peach500`) are reachable through [FoliTheme.colors].
 */
@Composable
fun FoliTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val palette = if (darkTheme) FoliColors.Night else FoliColors.Day

    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = palette.primary,
            onPrimary = palette.onPrimary,
            primaryContainer = palette.leaf100,
            onPrimaryContainer = palette.ink,
            secondary = palette.moss700,
            onSecondary = palette.onPrimary,
            background = palette.surface100,
            onBackground = palette.ink,
            surface = palette.surface200,
            onSurface = palette.ink,
            surfaceVariant = palette.surface300,
            onSurfaceVariant = palette.inkMuted,
            outline = palette.line,
            outlineVariant = palette.lineStrong,
            error = palette.clay400,
            onError = palette.onPrimary,
        )
    } else {
        lightColorScheme(
            primary = palette.primary,
            onPrimary = palette.onPrimary,
            primaryContainer = palette.leaf100,
            onPrimaryContainer = palette.ink,
            secondary = palette.moss700,
            onSecondary = palette.onPrimary,
            background = palette.surface100,
            onBackground = palette.ink,
            surface = palette.surface200,
            onSurface = palette.ink,
            surfaceVariant = palette.surface300,
            onSurfaceVariant = palette.inkMuted,
            outline = palette.line,
            outlineVariant = palette.lineStrong,
            error = palette.clay400,
            onError = palette.onPrimary,
        )
    }

    val typography = Typography(
        displayLarge = FoliTypography.displayXl,
        displayMedium = FoliTypography.displayL,
        displaySmall = FoliTypography.displayM,
        headlineLarge = FoliTypography.displayS,
        headlineMedium = FoliTypography.quote,
        headlineSmall = FoliTypography.numeral,
        titleLarge = FoliTypography.title,
        bodyLarge = FoliTypography.bodyL,
        bodyMedium = FoliTypography.body,
        labelLarge = FoliTypography.label,
        labelMedium = FoliTypography.button,
        bodySmall = FoliTypography.caption,
        labelSmall = FoliTypography.micro,
    )

    CompositionLocalProvider(LocalFoliColors provides palette) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            content = content,
        )
    }
}

/**
 * Entry points for Foli-specific tokens that don't have a direct Material 3
 * [MaterialTheme.colorScheme] slot. [colors] is theme-aware (day/night) and must
 * be read inside a [FoliTheme] composition; [spacing], [radius], [size], and
 * [typography] are the same fixed scale in both themes.
 */
object FoliTheme {
    val colors: FoliColorPalette
        @Composable get() = LocalFoliColors.current

    val spacing get() = FoliSpacing
    val radius get() = FoliRadius
    val size get() = FoliSize
    val typography get() = FoliTypography
}
