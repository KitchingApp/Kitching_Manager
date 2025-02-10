package com.kitching.app.navgraph

import androidx.compose.runtime.Composable

data class OtherMenuItem(
    val tabName: String = "",
    val routeName: String = ""
) {
    @Composable
    fun renderOtherMenuItems(): List<OtherMenuItem> {
        return listOf(
            OtherMenuItem(
                tabName = "초대코드",
                routeName = "InviteCode"
            ),
            OtherMenuItem(
                tabName = "공지사항",
                routeName = "NoticeList"
            ),
            OtherMenuItem(
                tabName = "직급관리",
                routeName = "StaffLevel"
            ),
            OtherMenuItem(
                tabName = "스케줄목록",
                routeName = "ScheduleTime"
            ),
            OtherMenuItem(
                tabName = "멤버관리",
                routeName = "MemberList"
            )
        )
    }
}