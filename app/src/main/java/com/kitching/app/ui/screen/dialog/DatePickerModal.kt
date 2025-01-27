package com.kitching.app.ui.screen.dialog

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.kitching.app.ui.theme.mainColor
import com.kitching.app.ui.theme.pretendard
import java.time.LocalDateTime
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    selectedDateTime: LocalDateTime,
    onDismissRequest: () -> Unit,
    onClickConfirm: (selectedDateMillis: Long?) -> Unit,
    onClickCancel: () -> Unit
) {

    val selectedDateInMillis = selectedDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDateInMillis
    )

    DatePickerDialog(
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(
                onClick = { onClickConfirm(datePickerState.selectedDateMillis) }
            ) {
                Text(
                    text = "확인",
                    color = mainColor,
                    fontWeight = FontWeight.Bold,
                    fontFamily = pretendard
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onClickCancel() }
            ) {
                Text(
                    text = "취소",
                    color = mainColor,
                    fontWeight = FontWeight.Bold,
                    fontFamily = pretendard
                )
            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = Color.White,
        )
    ) {

        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                containerColor = Color.White,
                dividerColor = Color(0xFF6200EA), // 보라색
                titleContentColor = Color(0xFF2962FF), // 파란색
                headlineContentColor = Color(0xFFFF5722), // 주황색
                weekdayContentColor = Color(0xFF4CAF50), // 녹색
                subheadContentColor = Color(0xFFFFC107), // 노란색
                navigationContentColor = Color(0xFF00BCD4), // 청록색
                selectedDayContainerColor = Color(0xFF9C27B0), // 보라색 (짙은)
                selectedDayContentColor = Color(0xFFFFFFFF), // 흰색
                selectedYearContentColor = Color(0xFFFFFFFF), // 흰색
                selectedYearContainerColor = Color(0xFF3F51B5), // 짙은 파란색
                todayDateBorderColor = Color(0xFFFF9800), // 밝은 주황색
                todayContentColor = Color(0xFF795548), // 갈색
                dateTextFieldColors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF1F8E9), // 연한 녹색
                    focusedIndicatorColor = Color(0xFFCDDC39), // 밝은 녹색
                    focusedTextColor = Color(0xFF009688), // 진한 청록색
                    focusedLabelColor = Color(0xFFE91E63), // 핑크색
                    focusedPlaceholderColor = Color(0xFF607D8B), // 짙은 회색-파란색
                    unfocusedContainerColor = Color(0xFFFFFDE7), // 연한 노란색
                )
            )
        )
    }
}