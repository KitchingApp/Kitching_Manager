package com.kitching.app.ui.screen.common.button.system

import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kitching.app.ui.theme.Body1
import com.kitching.app.ui.theme.Body1_m
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.NeutralGray400
import com.kitching.app.ui.theme.PrimaryGreen300

enum class ButtonSize(
    val width: Dp,
    val height: Dp,
) {
    SMALL(width = 112.dp, height = 32.dp),
    MEDIUM(width = 150.dp, height = 40.dp)
}

enum class ButtonPriority(
    val container: Color,
    val content: Color,
    val border: BorderStroke?,
    val textStyle: TextStyle
) {
    PRIMARY(container = PrimaryGreen300, content = NeutralGray0, border = null, textStyle = Body1),
    SECONDARY(
        container = NeutralGray0,
        content = NeutralGray400,
        border = BorderStroke(
            width = 1.dp,
            color = NeutralGray400
        ),
        textStyle = Body1_m
    )
}