package com.kitching.app.navgraph

import com.kitching.app.R

data class ScheduleTabItem(
    val tabName: Int = -1,
) {
    fun renderTabItems(): List<ScheduleTabItem> {
        return listOf(
            ScheduleTabItem(
                tabName = R.string.schedule_tab_item_fixed_schedule,
            ),
            ScheduleTabItem(
                tabName = R.string.schedule_tab_item_applied_schedule,
            )
        )
    }
}