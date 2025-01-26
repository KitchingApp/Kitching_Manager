package com.kitching.app.navgraph

import androidx.compose.runtime.Composable

data class ScheduleTabItem(
    val tabName: String = "",
    val routeName: String = ""
) {
    @Composable
    fun renderTabItems(): List<ScheduleTabItem> {
        return listOf(
            ScheduleTabItem(
                tabName = "스케줄 확정",
                routeName = "FixedSchedule"
            ),
            ScheduleTabItem(
                tabName = "스케줄 신청",
                routeName = "AppliedSchedule"
            )
        )
    }
}