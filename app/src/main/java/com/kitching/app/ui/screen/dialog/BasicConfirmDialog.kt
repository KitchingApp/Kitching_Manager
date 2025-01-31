package com.kitching.app.ui.screen.dialog

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.kitching.app.ui.theme.H3_m

//@Preview(showBackground = true)
@Composable
fun BasicConfirmDialog(
    message: String,
    confirmText: String,
    onClickConfirm: () -> Unit,
    cancelText: String,
    onClickCancel: () -> Unit
) {
    CommonDialogComponent(
        height = 152.dp,
        paddingTop = 40.dp,
        paddingBottom = 24.dp,
        radius = 8.dp,
        confirmText = confirmText,
        onClickConfirm = { onClickConfirm() },
        cancelText = cancelText,
        onClickCancel = { onClickCancel() }
    ) {
        Text(
            text = message,
            style = H3_m,
        )
    }
}