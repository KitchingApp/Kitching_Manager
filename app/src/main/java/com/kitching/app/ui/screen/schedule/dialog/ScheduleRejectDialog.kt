package com.kitching.app.ui.screen.schedule.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kitching.app.ui.screen.commondialog.CommonDialogComponent
import com.kitching.app.ui.theme.H3_m
import com.kitching.app.ui.theme.H5_m
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.NeutralGray300
import com.kitching.app.ui.theme.NeutralGray400
import com.kitching.app.ui.theme.NeutralGray800

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleRejectDialog(
    showDialog: MutableState<Boolean>
) {
    var rejectReason by remember { mutableStateOf("") }

    val interactionSource = remember { MutableInteractionSource() }

    CommonDialogComponent(
        height = 204.dp,
        paddingTop = 24.dp,
        paddingBottom = 24.dp,
        radius = 8.dp,
        confirmText = "거절",
        onClickConfirm = { },
        cancelText = "취소",
        onClickCancel = { showDialog.value = false }
    ) {
        Text(
            text = "스케줄 신청 거절",
            style = H3_m,
            color = NeutralGray800
        )
        BasicTextField(
            value = rejectReason,
            onValueChange = { rejectReason = it },
            modifier = Modifier
                .width(240.dp)
                .height(52.dp)
                .background(NeutralGray0, shape = RoundedCornerShape(8.dp))
                .border(1.dp, NeutralGray300, shape = RoundedCornerShape(8.dp)),
            textStyle = H5_m.copy(
                color = NeutralGray800,
                letterSpacing = 1.sp
            ),
            interactionSource = interactionSource,
            decorationBox = { innerTextField ->
                TextFieldDefaults.DecorationBox(
                    value = rejectReason,
                    innerTextField = {
                        Box(
                            modifier = Modifier.fillMaxSize().padding(start = 2.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            innerTextField()
                        }
                    },
                    singleLine = true,
                    enabled = true,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = interactionSource,
                    placeholder = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "신청 거절 사유를 입력해주세요",
                                style = H5_m.copy(color = NeutralGray400),
                                textAlign = TextAlign.Center
                            )
                        }
                    },
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(8.dp, 0.dp),
                    colors = TextFieldDefaults.colors().copy(
                        focusedIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = NeutralGray0,
                        focusedContainerColor = NeutralGray0,
                        errorContainerColor = NeutralGray0,
                        disabledContainerColor = NeutralGray0,
                        errorPlaceholderColor = NeutralGray400,
                        disabledPlaceholderColor = NeutralGray400,
                        focusedPlaceholderColor = NeutralGray400,
                        unfocusedPlaceholderColor = NeutralGray400,
                        unfocusedTextColor = NeutralGray800,
                        focusedTextColor = NeutralGray800,
                        disabledTextColor = NeutralGray800,
                        errorTextColor = NeutralGray800,
                        unfocusedSupportingTextColor = NeutralGray800,
                        errorSupportingTextColor = NeutralGray800,
                        disabledSupportingTextColor = NeutralGray800,
                        focusedSupportingTextColor = NeutralGray800
                    )
                )
            }
        )
    }
}