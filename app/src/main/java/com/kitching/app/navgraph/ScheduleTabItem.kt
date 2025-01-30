package com.kitching.app.navgraph

data class ScheduleTabItem(
    val tabName: String = "",
) {
    fun renderTabItems(): List<ScheduleTabItem> {
        return listOf(
            ScheduleTabItem(
                tabName = "스케줄 확정",
            ),
            ScheduleTabItem(
                tabName = "스케줄 신청",
            )
        )
    }
}