package com.kitching.app.ui.screen.common.button

import androidx.compose.runtime.Composable
import com.kitching.app.ui.screen.common.button.system.ButtonBase
import com.kitching.app.ui.screen.common.button.system.ButtonPriority
import com.kitching.app.ui.screen.common.button.system.ButtonSize

@Composable
fun SecondaryMediumButton(
    buttonText: String,
    onClick: () -> Unit
) {
    ButtonBase(
        buttonPriority = ButtonPriority.SECONDARY,
        buttonSize = ButtonSize.MEDIUM,
        buttonText = buttonText
    ) { onClick() }
}