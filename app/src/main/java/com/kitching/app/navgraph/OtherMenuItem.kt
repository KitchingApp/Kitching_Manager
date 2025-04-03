package com.kitching.app.navgraph

import androidx.compose.runtime.Composable
import com.kitching.app.R

data class OtherMenuItem(
    val tabName: Int,
    val destination: Route.OtherMenuItem
) {
    companion object {
        @Composable
        fun renderOtherMenuItems(): List<OtherMenuItem> {
            return listOf(
                OtherMenuItem(
                    tabName = R.string.invite_code,
                    destination = Route.OtherGraph.InviteCode
                ),
                OtherMenuItem(
                    tabName = R.string.notice,
                    destination = Route.OtherGraph.NoticeList
                ),
                OtherMenuItem(
                    tabName = R.string.staff_level_management,
                    destination = Route.OtherGraph.StaffLevel
                ),
                OtherMenuItem(
                    tabName = R.string.schedule_time,
                    destination = Route.OtherGraph.ScheduleTime
                ),
                OtherMenuItem(
                    tabName = R.string.member_management,
                    destination = Route.OtherGraph.MemberList
                )
            )
        }
    }
}