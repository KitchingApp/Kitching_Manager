package com.kitching.app.ui.screen.schedule.dialog

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.unit.dp
import com.kitching.app.ui.screen.commondialog.CommonDialogComponent
import com.kitching.app.ui.theme.H5
import com.kitching.app.ui.theme.NeutralGray800
import com.kitching.domain.AppResult
import com.kitching.domain.entities.Member
import com.kitching.domain.entities.MemberList
import com.kitching.domain.entities.ScheduleTime
import java.time.LocalDateTime

//@Preview
@Composable
fun ScheduleCreateDialog(
    selectedDateTime: LocalDateTime,
    isExpanded: MutableState<Boolean>,
    onDismissRequest: () -> Unit,
    onClickConfirm: () -> Unit,
    selectedMember: MutableState<Member>,
    members: List<Member>,
    scheduleTimes: List<ScheduleTime>,
    selectedScheduleTimes: MutableState<ScheduleTime>
) {
    CommonDialogComponent(
        height = 237.dp,
        paddingTop = 24.dp,
        paddingBottom = 32.dp,
        radius = 20.dp,
        confirmText = "배정",
        onClickConfirm = { onClickConfirm() },
        cancelText = "취소",
        onClickCancel = { onDismissRequest() }
    ) {
        Text(
            text = selectedDateTime.toLocalDate().toString(),
            style = H5,
            color = NeutralGray800
        )
        MemberSearchComponent(
            members = members,
            selectedMember = selectedMember,
            isExpanded = isExpanded
        )
        ScheduleTimeChipComponent(scheduleTimes, selectedScheduleTimes)
    }
}