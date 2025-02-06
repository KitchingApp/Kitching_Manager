package com.kitching.app.ui.screen.schedule

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.kitching.app.ui.item.AppliedScheduleItemUI
import com.kitching.app.ui.screen.schedule.dialog.ScheduleRejectDialog
import com.kitching.domain.entities.Schedule

//@Preview
@Composable
fun AppliedScheduleScreen(
    scheduleList: List<Schedule>,
    onApplyClick: (scheduleId: String) -> Unit,
    onRejectClick: (scheduleId: String) -> Unit
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
                        onRejectClick = { scheduleId -> onRejectClick(scheduleId) }
                    )
                }
            }
        }
    }
}