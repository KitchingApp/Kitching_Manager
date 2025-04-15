package com.kitching.app.util

import androidx.compose.ui.graphics.Color
import kotlin.math.roundToInt

/** hex 문자열을 받아 Color Int로 변환 */
fun hexToArgb(hex: String): Int {
    val r = Integer.parseInt(hex.substring(0, 2), 16)
    val g = Integer.parseInt(hex.substring(2, 4), 16)
    val b = Integer.parseInt(hex.substring(4, 6), 16)

    return (0xFF000000 or ((r shl 16).toLong()) or ((g shl 8).toLong()) or b.toLong()).toInt()
}

/** Color를 Hex 문자열로 변환 */
fun Color.toHex(includeAlpha: Boolean = false): String {
    val r = (red * 255).roundToInt()
    val g = (green * 255).roundToInt()
    val b = (blue * 255).roundToInt()
    val a = (alpha * 255).roundToInt()

    return if (includeAlpha) {
        String.format("%02X%02X%02X%02X", r, g, b, a)
    } else {
        String.format("%02X%02X%02X", r, g, b)
    }
}