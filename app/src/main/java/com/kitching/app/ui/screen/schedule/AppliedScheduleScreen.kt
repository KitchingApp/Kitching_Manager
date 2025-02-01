package com.kitching.app.ui.screen.schedule

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import com.kitching.app.ui.item.AppliedScheduleItemUI

//@Preview
@Composable
fun AppliedScheduleScreen(
    mutableState: MutableState<List<ScheduleDTO>>,
    scheduleList: State<List<ScheduleDTO>>
) {
    if(scheduleList.value.isEmpty()) {
        EmptyScheduleScreen()
    } else {
        LazyColumn {
            scheduleList.value.filter { !it.isFix }.forEach { schedule ->
                item {
                    AppliedScheduleItemUI(schedule, mutableState)
                }
            }
        }
    }
}