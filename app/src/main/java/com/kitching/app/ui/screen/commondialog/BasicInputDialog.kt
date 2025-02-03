package com.kitching.app.ui.screen.commondialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kitching.app.ui.theme.Body1
import com.kitching.app.ui.theme.CategoryColor1
import com.kitching.app.ui.theme.CategoryColor2
import com.kitching.app.ui.theme.CategoryColor3
import com.kitching.app.ui.theme.CategoryColor4
import com.kitching.app.ui.theme.CategoryColor5
import com.kitching.app.ui.theme.CategoryColor6
import com.kitching.app.ui.theme.H3_m
import com.kitching.app.ui.theme.H5_m
import com.kitching.app.ui.theme.NeutralGray300
import com.kitching.app.ui.theme.NeutralGray800

//@Preview(showBackground = true)
@Composable
fun BasicInputDialog(
    title: String,
    textState: MutableState<TextFieldValue>,
    placeHolder: String,
    confirmText: String,
    onClickConfirm: () -> Unit,
    cancelText: String,
    onClickCancel: () -> Unit
) {
    CommonDialogComponent(
        height = 204.dp,
        paddingTop = 40.dp,
        paddingBottom = 24.dp,
        radius = 8.dp,
        confirmText = confirmText,
        onClickConfirm = onClickConfirm,
        cancelText = cancelText,
        onClickCancel = onClickCancel
    ) {
        Text(
            text = title,
            style = H3_m,
        )
        Box(
            modifier = Modifier
                .width(240.dp).height(52.dp)
                .border(
                    border = BorderStroke(1.dp, NeutralGray300),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(20.dp, 0.dp)
        ) {
            // 텍스트가 비어 있을 때만 placeholder를 표시
            if (textState.value.text.isEmpty()) {
                Text(
                    text = placeHolder,
                    style = Body1.copy(color = NeutralGray300, textAlign = TextAlign.Start),
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }
            BasicTextField(
                modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                value = textState.value,
                onValueChange = {
                    textState.value = it
                },
                textStyle = Body1.copy(color = NeutralGray800, textAlign = TextAlign.Start),
                singleLine = true,
                cursorBrush = SolidColor(NeutralGray800),
            )
        }
    }
}