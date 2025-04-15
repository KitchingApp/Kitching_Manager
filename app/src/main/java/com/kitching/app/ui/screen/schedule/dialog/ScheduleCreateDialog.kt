package com.kitching.app.ui.screen.schedule.dialog

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.kitching.app.ui.screen.commondialog.CommonDialogComponent
import com.kitching.app.ui.theme.H5
import com.kitching.app.ui.theme.NeutralGray800
import com.kitching.domain.entities.Member
import com.kitching.domain.entities.ScheduleTime
import java.time.LocalDateTime

@Composable
fun ScheduleCreateDialog(
    selectedDateTime: LocalDateTime,
    onDismissRequest: () -> Unit,
    onClickConfirm: (selectedMember: Member, selectedScheduleTime: ScheduleTime) -> Unit,
    members: List<Member>,
    scheduleTimes: List<ScheduleTime>
) {
    /** 드롭다운에서 선택된 멤버 */
    var selectedMember by remember { mutableStateOf(Member.init()) }
    /** 선택된 스케줄타임 */
    var selectedScheduleTime by remember { mutableStateOf(scheduleTimes.firstOrNull() ?: ScheduleTime.init()) }

    CommonDialogComponent(
        height = 237.dp,
        paddingTop = 24.dp,
        paddingBottom = 32.dp,
        radius = 20.dp,
        confirmText = "배정",
        onClickConfirm = { onClickConfirm(selectedMember, selectedScheduleTime) },
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
            onMemberSelected = { member -> selectedMember = member },
            selectedMember = selectedMember
        )
        ScheduleTimeChipComponent(
            scheduleTimes = scheduleTimes,
            selectedScheduleTime = selectedScheduleTime,
            onScheduleTimeSelected = { scheduleTime -> selectedScheduleTime = scheduleTime })
    }
}