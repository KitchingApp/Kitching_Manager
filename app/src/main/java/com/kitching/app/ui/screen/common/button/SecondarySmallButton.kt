package com.kitching.app.ui.screen.common.button

import androidx.compose.runtime.Composable
import com.kitching.app.ui.screen.common.button.system.ButtonBase
import com.kitching.app.ui.screen.common.button.system.ButtonPriority
import com.kitching.app.ui.screen.common.button.system.ButtonSize

@Composable
fun SecondarySmallButton(
    buttonText: String,
    onClick: () -> Unit
) {
    ButtonBase(
        buttonPriority = ButtonPriority.PRIMARY,
        buttonSize = ButtonSize.SMALL,
        buttonText = buttonText
    ) { onClick() }
}