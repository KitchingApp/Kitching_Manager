package com.kitching.app.ui.screen.schedule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

data class ScheduleDTO(
    val scheduleId: String,
    val date: String,
    val departmentName: String?,
    val userId: String,
    val userName: String,
    val scheduleTimeName: String,
    val isFix: Boolean
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleTabContent(
    pagerState: PagerState,
    selectedDateTime: String
) {
    val schedulesMockData = remember {
            mutableStateOf(
                listOf(
                    ScheduleDTO(
                        scheduleId = "1",
                        date = "2025-02-01",
                        departmentName = "주방",
                        userId = "a1",
                        userName = "박민수",
                        scheduleTimeName = "오픈",
                        isFix = true
                    ),
                    ScheduleDTO(
                        scheduleId = "2",
                        date = "2025-02-01",
                        departmentName = "홀",
                        userId = "b2",
                        userName = "박지수",
                        scheduleTimeName = "미들",
                        isFix = false
                    ),
                    ScheduleDTO(
                        scheduleId = "3",
                        date = "2025-02-01",
                        departmentName = "주방",
                        userId = "c3",
                        userName = "김채연",
                        scheduleTimeName = "마감",
                        isFix = true
                    ),
                    ScheduleDTO(
                        scheduleId = "4",
                        date = "2025-02-01",
                        departmentName = "홀",
                        userId = "d4",
                        userName = "김수연",
                        scheduleTimeName = "오픈",
                        isFix = false
                    ),
                    ScheduleDTO(
                        scheduleId = "5",
                        date = "2025-02-01",
                        departmentName = "주방",
                        userId = "e5",
                        userName = "최다혜",
                        scheduleTimeName = "미들",
                        isFix = true
                    ),
                    ScheduleDTO(
                        scheduleId = "6",
                        date = "2025-02-01",
                        departmentName = "홀",
                        userId = "f6",
                        userName = "최정혜",
                        scheduleTimeName = "마감",
                        isFix = false
                    ),
                    ScheduleDTO(
                        scheduleId = "7",
                        date = "2025-02-01",
                        departmentName = "주방",
                        userId = "g7",
                        userName = "원정문",
                        scheduleTimeName = "오픈",
                        isFix = true
                    ),
                    ScheduleDTO(
                        scheduleId = "8",
                        date = "2025-02-01",
                        departmentName = "홀",
                        userId = "h8",
                        userName = "김은별",
                        scheduleTimeName = "미들",
                        isFix = false
                    ),
                    ScheduleDTO(
                        scheduleId = "9",
                        date = "2025-02-01",
                        departmentName = "주방",
                        userId = "i9",
                        userName = "이민제",
                        scheduleTimeName = "마감",
                        isFix = true
                    ),
                    ScheduleDTO(
                        scheduleId = "10",
                        date = "2025-02-01",
                        departmentName = "홀",
                        userId = "j10",
                        userName = "손지아",
                        scheduleTimeName = "오픈",
                        isFix = false
                    )
                )
            )
        }
    val scheduleList = remember(selectedDateTime) {
        derivedStateOf {
            schedulesMockData.value.filter { it.date == selectedDateTime }
                .sortedBy { it.scheduleTimeName }
        }
    }

    HorizontalPager(state = pagerState) { page ->
        when (page) {
            0 -> FixedScheduleScreen(schedulesMockData, scheduleList)
            1 -> AppliedScheduleScreen(schedulesMockData, scheduleList)
        }
    }
}