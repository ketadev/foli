package com.ketadev.foli.core.designsystem

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Text styles compiled from `design/tokens/design-tokens.json` (`typography.styles`).
 *
 * The JSON's `display` family names **Young Serif** and its `sans` family names
 * **Nunito** (both Google Fonts). Neither typeface's font files or license are
 * bundled in this module yet, so `display` styles fall back to [FontFamily.Serif]
 * and `sans` styles fall back to [FontFamily.SansSerif] until distributable font
 * assets and rights are confirmed. Swapping in the real families later only
 * requires changing [Display] and [Sans] here.
 */
object FoliTypography {
    private val Display: FontFamily = FontFamily.Serif
    private val Sans: FontFamily = FontFamily.SansSerif

    /** `display-xl`: celebrations and welcome screens. */
    val displayXl = TextStyle(
        fontFamily = Display,
        fontSize = 36.sp,
        lineHeight = 40.sp,
        fontWeight = FontWeight.Normal,
    )

    /** `display-l`: tab title (Library, Garden, Ideas). */
    val displayL = TextStyle(
        fontFamily = Display,
        fontSize = 30.sp,
        lineHeight = 35.sp,
        fontWeight = FontWeight.Normal,
    )

    /** `display-m`: main question of a screen. */
    val displayM = TextStyle(
        fontFamily = Display,
        fontSize = 27.sp,
        lineHeight = 31.sp,
        fontWeight = FontWeight.Normal,
    )

    /** `display-s`: title next to the back button. */
    val displayS = TextStyle(
        fontFamily = Display,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeight.Normal,
    )

    /** `quote`: ideas quoted by the reader. */
    val quote = TextStyle(
        fontFamily = Display,
        fontSize = 18.sp,
        lineHeight = 26.sp,
        fontWeight = FontWeight.Normal,
    )

    /** `numeral`: large counters such as page number or session time. */
    val numeral = TextStyle(
        fontFamily = Display,
        fontSize = 64.sp,
        lineHeight = 64.sp,
        fontWeight = FontWeight.Normal,
    )

    /** `title`: book title in the main card. */
    val title = TextStyle(
        fontFamily = Sans,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.ExtraBold,
    )

    /** `body-l`: intro text under a display style. */
    val bodyL = TextStyle(
        fontFamily = Sans,
        fontSize = 17.sp,
        lineHeight = 25.sp,
        fontWeight = FontWeight.Normal,
    )

    /** `body`: running text, ideas, and fields. */
    val body = TextStyle(
        fontFamily = Sans,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        fontWeight = FontWeight.Normal,
    )

    /** `label`: row and small-card titles. */
    val label = TextStyle(
        fontFamily = Sans,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.ExtraBold,
    )

    /** `button`: main button text (medium buttons use 15sp, see [label]). */
    val button = TextStyle(
        fontFamily = Sans,
        fontSize = 17.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.ExtraBold,
    )

    /** `caption`: metadata under a title. */
    val caption = TextStyle(
        fontFamily = Sans,
        fontSize = 13.sp,
        lineHeight = 18.sp,
        fontWeight = FontWeight.SemiBold,
    )

    /** `overline`: uppercase panel heading, `letterSpacing` of 0.02em (0.26sp at 13sp). */
    val overline = TextStyle(
        fontFamily = Sans,
        fontSize = 13.sp,
        lineHeight = 16.sp,
        fontWeight = FontWeight.ExtraBold,
        letterSpacing = 0.26.sp,
    )

    /** `micro`: tab-bar and achievement labels, the smallest style. */
    val micro = TextStyle(
        fontFamily = Sans,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontWeight = FontWeight.Bold,
    )
}
