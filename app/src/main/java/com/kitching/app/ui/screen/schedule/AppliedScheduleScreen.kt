package com.kitching.app.ui.screen.schedule

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.kitching.app.ui.item.AppliedScheduleItemUI
import com.kitching.app.ui.screen.commondialog.BasicConfirmDialog
import com.kitching.app.ui.screen.schedule.dialog.ScheduleRejectDialog

//@Preview
@Composable
fun AppliedScheduleScreen(
    mutableState: MutableState<List<ScheduleDTO>>,
    scheduleList: State<List<ScheduleDTO>>
) {
    val showRejectDialog = remember { mutableStateOf(false) }

    if(scheduleList.value.isEmpty()) {
        EmptyScheduleScreen()
    } else {
        LazyColumn {
            scheduleList.value.filter { !it.isFix }.forEach { schedule ->
                item {
                    AppliedScheduleItemUI(schedule, mutableState, showRejectDialog)
                }
            }
        }
    }
    if(showRejectDialog.value) {
        ScheduleRejectDialog(showRejectDialog)
    }
}