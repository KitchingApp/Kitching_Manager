package com.kitching.data.datasource

import com.kitching.data.dto.ScheduleDTO

interface ScheduleDataSource {
    suspend fun getSchedules(teamId: String, dateString: String): List<ScheduleDTO>

    suspend fun getSchedule(scheduleId: String): ScheduleDTO

    suspend fun createSchedule(teamId: String, dateString: String, userId: String, scheduleTimeId: String, fix: Boolean = true)

    suspend fun deleteSchedule(scheduleId: String)

    suspend fun applySchedule(scheduleId: String)
}