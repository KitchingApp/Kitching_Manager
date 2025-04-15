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
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kitching.app.R
import com.kitching.app.ui.theme.Body1
import com.kitching.app.ui.theme.H3_m
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.NeutralGray300
import com.kitching.app.ui.theme.NeutralGray800
import com.kitching.app.ui.theme.ColorList

//@Preview(showBackground = true)
@Composable
fun ColorInputDialog(
    title: String,
    placeHolder: String,
    textState: MutableState<TextFieldValue>,
    colorState: MutableState<Color>,
    confirmText: String,
    onClickConfirm: () -> Unit,
    cancelText: String,
    onClickCancel: () -> Unit
) {
    CommonDialogComponent(
        height = 261.dp,
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
                .background(color = colorState.value, shape = RoundedCornerShape(8.dp))
        ) {
            // 텍스트가 비어 있을 때만 placeholder를 표시
            if (textState.value.text.isEmpty()) {
                Text(
                    text = placeHolder,
                    style = Body1.copy(color = NeutralGray300, textAlign = TextAlign.Start),
                    modifier = Modifier.align(Alignment.CenterStart).padding(20.dp, 0.dp)
                )
            }
            BasicTextField(
                modifier = Modifier.fillMaxWidth().align(Alignment.Center).padding(20.dp, 0.dp),
                value = textState.value,
                onValueChange = {
                    textState.value = it
                },
                textStyle = Body1.copy(color = NeutralGray800, textAlign = TextAlign.Start),
                singleLine = true,
                cursorBrush = SolidColor(NeutralGray800),
            )
        }
        Row(
            modifier = Modifier.width(240.dp)
                .selectableGroup(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ColorList.forEach { color ->
                Box(
                    modifier = Modifier
                        .selectable(
                        selected = (color == colorState.value),
                        onClick = { colorState.value = color },
                        role = Role.RadioButton,
                    ).background(
                        color = Color(color.toArgb()),
                        shape = CircleShape
                    ).size(32.73.dp),
                    contentAlignment = Alignment.Center
                ) {
                    RadioButton(
                        modifier = Modifier.fillMaxSize(),
                        selected = (color == colorState.value),
                        onClick = null,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.Transparent,
                            unselectedColor = Color.Transparent,
                            disabledSelectedColor = Color.Transparent,
                            disabledUnselectedColor = Color.Transparent,
                        )
                    )
                    if(color == colorState.value) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.icon_check),
                            contentDescription = null,
                            tint = NeutralGray0,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}