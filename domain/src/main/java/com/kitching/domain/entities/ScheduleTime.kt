package com.kitching.domain.entities

import java.time.LocalTime

data class ScheduleTime(
    val scheduleTimeId: String,
    val scheduleTimeName: String,
    val startTime: LocalTime,
    val endTime: LocalTime,
)
