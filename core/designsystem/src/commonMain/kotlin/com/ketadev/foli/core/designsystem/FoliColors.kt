package com.ketadev.foli.core.designsystem

import androidx.compose.ui.graphics.Color

/**
 * One Foli color palette (day or night), holding every color token from
 * `design/tokens/design-tokens.json` (`color.day` / `color.night`).
 *
 * Field names mirror the JSON keys with hyphens removed (e.g. `surface-100`
 * becomes [surface100], `on-primary` becomes [onPrimary]).
 */
data class FoliColorPalette(
    val surface100: Color,
    val surface200: Color,
    val surface300: Color,
    val surfaceCelebrate: Color,
    val line: Color,
    val lineStrong: Color,
    val ink: Color,
    val inkMuted: Color,
    val inkSubtle: Color,
    val primary: Color,
    val primaryPress: Color,
    val onPrimary: Color,
    val moss700: Color,
    val leaf400: Color,
    val leaf300: Color,
    val leaf100: Color,
    val borderLeaf: Color,
    val sky100: Color,
    val hill200: Color,
    val butter300: Color,
    val peach100: Color,
    val peach300: Color,
    val peach500: Color,
    val ember700: Color,
    val clay400: Color,
    val clay300: Color,
    val blush: Color,
    val bark: Color,
    val lavender100: Color,
    val lavender500: Color,
    val lavender700: Color,
    val rest100: Color,
)

/**
 * Day and night color palettes compiled from `design/tokens/design-tokens.json`
 * (`color.day` and `color.night`). This object is the source of truth for those
 * hand-mapped hex values at compile time: the JSON file is never read at runtime,
 * see [FoliTheme].
 */
object FoliColors {
    val Day = FoliColorPalette(
        surface100 = Color(0xFFF7F2E9),
        surface200 = Color(0xFFFFFDF8),
        surface300 = Color(0xFFF3EEE3),
        surfaceCelebrate = Color(0xFFFBF3E4),
        line = Color(0xFFEDE5D6),
        lineStrong = Color(0xFFD9D0BF),
        ink = Color(0xFF27332C),
        inkMuted = Color(0xFF56625B),
        inkSubtle = Color(0xFF66716A),
        primary = Color(0xFF4F7556),
        primaryPress = Color(0xFF3C5E43),
        onPrimary = Color(0xFFFFFDF8),
        moss700 = Color(0xFF3C5E43),
        leaf400 = Color(0xFF7FA57A),
        leaf300 = Color(0xFFA9C2A0),
        leaf100 = Color(0xFFEEF3EC),
        borderLeaf = Color(0xFFC9D6C4),
        sky100 = Color(0xFFDCE8E6),
        hill200 = Color(0xFFC9DBC3),
        butter300 = Color(0xFFF5DFA0),
        peach100 = Color(0xFFFBE6D6),
        peach300 = Color(0xFFF3C9A9),
        peach500 = Color(0xFFE8A07A),
        ember700 = Color(0xFF8A4A2B),
        clay400 = Color(0xFFD98B6A),
        clay300 = Color(0xFFE39E7E),
        blush = Color(0xFFF3A98A),
        bark = Color(0xFF8A6A4F),
        lavender100 = Color(0xFFF1EDF6),
        lavender500 = Color(0xFF8C7DB5),
        lavender700 = Color(0xFF5B4F78),
        rest100 = Color(0xFFEFEAF3),
    )

    val Night = FoliColorPalette(
        surface100 = Color(0xFF1F3438),
        surface200 = Color(0xFF26403F),
        surface300 = Color(0xFF2F4B4F),
        surfaceCelebrate = Color(0xFF1F3438),
        line = Color(0xFF34575A),
        lineStrong = Color(0xFF4A6B6E),
        ink = Color(0xFFF5ECD7),
        inkMuted = Color(0xFFB9C8C2),
        inkSubtle = Color(0xFF9FB2AB),
        primary = Color(0xFFF5DFA0),
        primaryPress = Color(0xFFD9BD72),
        onPrimary = Color(0xFF27332C),
        moss700 = Color(0xFFC9DBC3),
        leaf400 = Color(0xFF8FB88A),
        leaf300 = Color(0xFFA9C2A0),
        leaf100 = Color(0xFF2B474A),
        borderLeaf = Color(0xFF4A6B6E),
        sky100 = Color(0xFF1F3438),
        hill200 = Color(0xFF2F4B4F),
        butter300 = Color(0xFFF5DFA0),
        peach100 = Color(0xFF4A3A33),
        peach300 = Color(0xFFF3C9A9),
        peach500 = Color(0xFFE8A07A),
        ember700 = Color(0xFFF3C9A9),
        clay400 = Color(0xFFC98062),
        clay300 = Color(0xFFD98B6A),
        blush = Color(0xFFF3A98A),
        bark = Color(0xFF8A6A4F),
        lavender100 = Color(0xFF3A3650),
        lavender500 = Color(0xFFB3A7D6),
        lavender700 = Color(0xFFD6CFE6),
        rest100 = Color(0xFF2A2C44),
    )
}
