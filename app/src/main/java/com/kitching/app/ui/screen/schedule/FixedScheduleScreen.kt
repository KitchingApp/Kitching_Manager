package com.kitching.app.ui.screen.schedule

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.kitching.app.ui.item.FixedScheduleItemUI
import com.kitching.app.ui.screen.commondialog.BasicConfirmDialog

//@Preview
@Composable
fun FixedScheduleScreen(
    mutableState: MutableState<List<ScheduleDTO>>,
    scheduleList: State<List<ScheduleDTO>>
) {
    val showDeleteDialog = remember { mutableStateOf(false) }

    if(scheduleList.value.isEmpty()) {
        EmptyScheduleScreen()
    } else {
        LazyColumn {
            scheduleList.value.filter { it.isFix }.forEach { schedule ->
                item {
                    FixedScheduleItemUI(schedule, mutableState, showDeleteDialog)
                }
            }
        }
    }
    if(showDeleteDialog.value){
        BasicConfirmDialog(
            message = "스케줄을 삭제하시겠습니까?",
            confirmText = "삭제",
            onClickConfirm = {},
            cancelText = "취소",
            onClickCancel = { showDeleteDialog.value = false }
        )
    }
}