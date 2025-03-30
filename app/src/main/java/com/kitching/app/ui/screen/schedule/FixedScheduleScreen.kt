package com.kitching.app.ui.screen.schedule

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.kitching.app.ui.item.FixedScheduleItemUI
import com.kitching.domain.entities.Schedule

@Composable
fun FixedScheduleScreen(
    scheduleList: List<Schedule>,
    onDeleteClick: (scheduleId: String) -> Unit
) {

    if(scheduleList.isEmpty()) {
        EmptyScheduleScreen()
    } else {
        LazyColumn {
            items(items = scheduleList, key = {it.scheduleId}) {
                FixedScheduleItemUI(
                    schedule = it,
                    onDeleteClick = { scheduleId -> onDeleteClick(scheduleId) }
                )
            }
        }
    }

}