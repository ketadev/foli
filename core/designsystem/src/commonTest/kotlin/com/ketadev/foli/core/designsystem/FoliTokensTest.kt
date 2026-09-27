package com.ketadev.foli.core.designsystem

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Checks representative compiled token values against
 * `design/tokens/design-tokens.json` (read-only source, not read at test or
 * runtime).
 *
 * No-runtime-JSON-parsing verification: `core/designsystem/build.gradle.kts`
 * declares no `kotlinx-serialization-json` and no Compose Multiplatform
 * resources dependency; this module's only `commonMain` dependencies are
 * `compose.runtime`, `compose.foundation`, `compose.material3`, and
 * `compose.ui`. The assertions below exercise only compiled Kotlin constants
 * ([FoliColors], [FoliSpacing], [FoliRadius], [FoliSize], [FoliTypography]),
 * which is what makes token values available without a runtime file read.
 */
class FoliTokensTest {

    @Test
    fun dayColorsMatchDesignTokens() {
        assertEquals(Color(0xFFF7F2E9), FoliColors.Day.surface100)
        assertEquals(Color(0xFF27332C), FoliColors.Day.ink)
        assertEquals(Color(0xFF4F7556), FoliColors.Day.primary)
        assertEquals(Color(0xFFFFFDF8), FoliColors.Day.onPrimary)
        assertEquals(Color(0xFF7FA57A), FoliColors.Day.leaf400)
        assertEquals(Color(0xFFE8A07A), FoliColors.Day.peach500)
        assertEquals(Color(0xFF5B4F78), FoliColors.Day.lavender700)
        assertEquals(Color(0xFFEFEAF3), FoliColors.Day.rest100)
    }

    @Test
    fun nightColorsMatchDesignTokens() {
        assertEquals(Color(0xFF1F3438), FoliColors.Night.surface100)
        assertEquals(Color(0xFFF5ECD7), FoliColors.Night.ink)
        // Night's primary is butter-300, not moss green: the JSON's day/night
        // swap for the "primary" role (see colorUsage.primary in the source).
        assertEquals(Color(0xFFF5DFA0), FoliColors.Night.primary)
        assertEquals(Color(0xFF27332C), FoliColors.Night.onPrimary)
        assertEquals(Color(0xFF8FB88A), FoliColors.Night.leaf400)
        assertEquals(Color(0xFFE8A07A), FoliColors.Night.peach500)
        assertEquals(Color(0xFFD6CFE6), FoliColors.Night.lavender700)
        assertEquals(Color(0xFF2A2C44), FoliColors.Night.rest100)
    }

    @Test
    fun spacingMatchesDesignTokens() {
        assertEquals(4.dp, FoliSpacing.space1)
        assertEquals(16.dp, FoliSpacing.space4)
        assertEquals(24.dp, FoliSpacing.space6)
        assertEquals(56.dp, FoliSpacing.space14)
    }

    @Test
    fun radiusMatchesDesignTokens() {
        assertEquals(6.dp, FoliRadius.sm)
        assertEquals(20.dp, FoliRadius.xl)
        assertEquals(36.dp, FoliRadius.sheet)
        assertEquals(999.dp, FoliRadius.pill)
    }

    @Test
    fun sizeMatchesDesignTokens() {
        assertEquals(44.dp, FoliSize.tapMin)
        assertEquals(58.dp, FoliSize.buttonLg)
        assertEquals(84.dp, FoliSize.tabBarHeight)
        assertEquals(8.dp, FoliSize.progressHeight)
    }

    @Test
    fun typographyMatchesDesignTokens() {
        assertEquals(36.sp, FoliTypography.displayXl.fontSize)
        assertEquals(40.sp, FoliTypography.displayXl.lineHeight)
        assertEquals(FontWeight.Normal, FoliTypography.displayXl.fontWeight)
        assertEquals(FontFamily.Serif, FoliTypography.displayXl.fontFamily)

        assertEquals(16.sp, FoliTypography.body.fontSize)
        assertEquals(22.sp, FoliTypography.body.lineHeight)
        assertEquals(FontFamily.SansSerif, FoliTypography.body.fontFamily)

        assertEquals(17.sp, FoliTypography.button.fontSize)
        assertEquals(FontWeight.ExtraBold, FoliTypography.button.fontWeight)

        // overline: letterSpacing of 0.02em at 13sp == 0.26sp.
        assertEquals(13.sp, FoliTypography.overline.fontSize)
        assertEquals(0.26.sp, FoliTypography.overline.letterSpacing)

        assertEquals(12.sp, FoliTypography.micro.fontSize)
        assertEquals(FontWeight.Bold, FoliTypography.micro.fontWeight)
    }
}
