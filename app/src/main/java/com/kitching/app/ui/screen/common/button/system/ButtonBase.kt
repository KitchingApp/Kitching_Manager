package com.kitching.app.ui.screen.common.button.system

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ButtonBase(
    buttonPriority: ButtonPriority,
    buttonSize: ButtonSize,
    buttonText: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .width(buttonSize.width)
            .height(buttonSize.height),
        onClick = { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.textButtonColors().copy(
            contentColor = buttonPriority.content,
            containerColor = buttonPriority.container
        ),
        border = buttonPriority.border,
        contentPadding = PaddingValues(0.dp),
    ) {
        Text(
            text = buttonText,
            style = buttonPriority.textStyle
        )
    }
}