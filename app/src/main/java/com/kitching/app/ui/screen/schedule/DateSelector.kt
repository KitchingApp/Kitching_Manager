package com.kitching.app.ui.screen.schedule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kitching.app.R
import com.kitching.app.ui.theme.subTextColor
import java.time.LocalDateTime

/**
 * 날짜 선택 컴포넌트
 * ex) < 2025-01-26 >
 * */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateSelector(
    selectedDateTime: LocalDateTime,
    onDateChange: (LocalDateTime) -> Unit, // 날짜 변경시 호출
    onClickDateBtn: () -> Unit // 날짜 텍스트 버튼 클릭시 호출
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
        ) {
            IconButton(onClick = {
                onDateChange(selectedDateTime.minusDays(1))
            }) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.icon_left_triangle),
                    contentDescription = "prev date button",
                    tint = subTextColor
                )
            }
            TextButton(
                modifier = Modifier
                    .padding(13.dp, 0.dp)
                    .align(Alignment.CenterVertically),
                onClick = {
                    onClickDateBtn()
                },
            ) {
                Text(
                    text = selectedDateTime.toLocalDate().toString(),
                    color = subTextColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            IconButton(onClick = {
                onDateChange(selectedDateTime.plusDays(1))
            }) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.icon_right_triangle),
                    contentDescription = "next date button",
                    tint = subTextColor
                )
            }
        }
    }
}