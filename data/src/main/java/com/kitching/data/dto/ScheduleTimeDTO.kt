package com.kitching.data.dto

data class ScheduleTimeDTO(
    val id: String,
    val teamId: String,
    val name: String,
    val startTime: String,
    val endTime: String,
    val color: String,
)
