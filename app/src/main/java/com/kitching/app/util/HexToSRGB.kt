package com.kitching.app.util

/** hex 문자열을 받아 Color Int로 변환 */
fun hexToArgb(hex: String): Int {
    val r = Integer.parseInt(hex.substring(1, 3), 16)
    val g = Integer.parseInt(hex.substring(3, 5), 16)
    val b = Integer.parseInt(hex.substring(5, 7), 16)

    return (0xFF000000 or ((r shl 16).toLong()) or ((g shl 8).toLong()) or b.toLong()).toInt()
}