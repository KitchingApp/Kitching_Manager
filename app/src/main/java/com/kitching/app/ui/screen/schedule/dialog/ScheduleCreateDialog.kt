package com.kitching.app.ui.screen.schedule.dialog

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.kitching.app.ui.screen.commondialog.CommonDialogComponent
import com.kitching.app.ui.theme.H5
import com.kitching.app.ui.theme.NeutralGray800
import java.time.LocalDateTime

data class DropDownMembersDTO(
    val userId: String,
    val userName: String
)

data class ScheduleTimeChipsDTO(
    val scheduleTimeId: String,
    val scheduleTimeName: String
)

//@Preview
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleCreateDialog(
    selectedDateTime: LocalDateTime,
    isExpandedRemember: MutableState<Boolean>,
    onDismissRequest: () -> Unit,
) {
    /** 선택된 멤버를 저장 */
    val selectedMember = remember {
        mutableStateOf(
            DropDownMembersDTO(
                userId = "",
                userName = ""
            )
        )
    }

    CommonDialogComponent(
        height = 237.dp,
        paddingTop = 24.dp,
        paddingBottom = 32.dp,
        radius = 20.dp,
        confirmText = "배정",
        onClickConfirm = {  },
        cancelText = "취소",
        onClickCancel = { onDismissRequest() }
    ) {
        Text(
            text = selectedDateTime.toLocalDate().toString(),
            style = H5,
            color = NeutralGray800
        )
        MemberSearchComponent(
            selectedMemberRemember = selectedMember,
            isExpandedRemember = isExpandedRemember
        )
        ScheduleTimeChipComponent()
    }
}