package com.kitching.app.ui.screen.schedule

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.kitching.app.ui.item.FixedScheduleItemUI
import com.kitching.app.ui.screen.commondialog.BasicConfirmDialog
import com.kitching.domain.entities.Schedule

//@Preview
@Composable
fun FixedScheduleScreen(
    scheduleList: List<Schedule>,
    onDeleteClick: (scheduleId: String) -> Unit
) {

    if(scheduleList.isEmpty()) {
        EmptyScheduleScreen()
    } else {
        LazyColumn {
            scheduleList.forEach { schedule ->
                item(key = schedule.scheduleId) {
                    FixedScheduleItemUI(
                        schedule = schedule,
                        onDeleteClick = { scheduleId -> onDeleteClick(scheduleId) }
                    )
                }
            }
        }
    }

}