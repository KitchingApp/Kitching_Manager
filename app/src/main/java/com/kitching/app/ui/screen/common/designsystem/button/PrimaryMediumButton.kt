package com.kitching.app.ui.screen.common.designsystem.button

import androidx.compose.runtime.Composable
import com.kitching.app.ui.screen.common.designsystem.button.system.ButtonBase
import com.kitching.app.ui.screen.common.designsystem.button.system.ButtonPriority
import com.kitching.app.ui.screen.common.designsystem.button.system.ButtonSize

@Composable
fun PrimaryMediumButton(
    buttonText: String,
    onClick: () -> Unit
) {
    ButtonBase(
        buttonPriority = ButtonPriority.PRIMARY,
        buttonSize = ButtonSize.MEDIUM,
        buttonText = buttonText
    ) { onClick() }
}