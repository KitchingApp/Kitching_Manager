package com.kitching.domain.entities


data class ScheduleTime(
    val scheduleTimeId: String = "",
    val scheduleTimeName: String = "",
    val startTime: String = "",
    val endTime: String = "",
) {
    companion object {
        fun init() = ScheduleTime("", "", "00:00", "00:00")
    }
}
