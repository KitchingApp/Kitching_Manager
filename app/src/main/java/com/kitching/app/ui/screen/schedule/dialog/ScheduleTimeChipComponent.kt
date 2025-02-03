package com.kitching.app.ui.screen.schedule.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kitching.app.ui.theme.H5_m
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.NeutralGray800
import com.kitching.app.ui.theme.PrimaryGreen300
import com.kitching.app.ui.theme.SecondaryLightGreen100

@Preview
@Composable
fun ScheduleTimeChipComponent() {
    val selectedScheduleTime = remember {
        mutableStateOf(
            ScheduleTimeChipsDTO(
                scheduleTimeId = "",
                scheduleTimeName = ""
            )
        )
    }

    val scheduleTimeListMockData = listOf(
        ScheduleTimeChipsDTO(
            scheduleTimeId = "1",
            scheduleTimeName = "오픈"
        ),
        ScheduleTimeChipsDTO(
            scheduleTimeId = "2",
            scheduleTimeName = "미들"
        ),
        ScheduleTimeChipsDTO(
            scheduleTimeId = "3",
            scheduleTimeName = "마감"
        )
    )

    Row(
        modifier = Modifier.width(240.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        scheduleTimeListMockData.forEach { scheduleTime ->
            FilterChip(
                onClick = {
                    selectedScheduleTime.value = scheduleTime
                },
                label = {
                    Text(
                        text = scheduleTime.scheduleTimeName,
                        style = H5_m
                    )
                },
                selected = selectedScheduleTime.value == scheduleTime,
                colors = SelectableChipColors(
                    containerColor = SecondaryLightGreen100,
                    labelColor = NeutralGray800,
                    leadingIconColor = NeutralGray800,
                    trailingIconColor = NeutralGray800,
                    disabledContainerColor = SecondaryLightGreen100,
                    disabledLabelColor = NeutralGray800,
                    disabledLeadingIconColor = NeutralGray800,
                    disabledTrailingIconColor = NeutralGray800,
                    selectedContainerColor = PrimaryGreen300,
                    disabledSelectedContainerColor = PrimaryGreen300,
                    selectedLabelColor = NeutralGray0,
                    selectedLeadingIconColor = NeutralGray0,
                    selectedTrailingIconColor = NeutralGray0
                ),
                border = null,
                shape = RoundedCornerShape(10.dp)
            )
        }
    }
}