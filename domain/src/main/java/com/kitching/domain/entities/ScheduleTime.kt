package com.kitching.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class ScheduleTime(
    val scheduleTimeId: String,
    val scheduleTimeName: String,
    val startTime: String,
    val endTime: String,
)
