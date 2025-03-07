package com.kitching.app.ui.screen.schedule

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.kitching.app.ui.item.AppliedScheduleItemUI
import com.kitching.domain.entities.Schedule

//@Preview
@Composable
fun AppliedScheduleScreen(
    scheduleList: List<Schedule>,
    onApplyClick: (scheduleId: String) -> Unit,
    onRejectClick: (schedule: Schedule) -> Unit
) {

    if(scheduleList.isEmpty()) {
        EmptyScheduleScreen()
    } else {
        LazyColumn {
            scheduleList.forEach { schedule ->
                item {
                    AppliedScheduleItemUI(
                        schedule = schedule,
                        onApplyClick = { scheduleId -> onApplyClick(scheduleId) },
                        onRejectClick = { schedule -> onRejectClick(schedule) }
                    )
                }
            }
        }
    }
}