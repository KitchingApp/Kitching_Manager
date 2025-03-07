package com.kitching.data.datasource

import com.kitching.data.dto.ScheduleDTO

interface ScheduleDataSource {
    suspend fun getSchedules(teamId: String, dateString: String): List<ScheduleDTO>

    suspend fun getSchedule(scheduleId: String): ScheduleDTO

    suspend fun createSchedule(teamId: String, dateString: String, userId: String, scheduleTimeId: String, fix: Boolean = true): Boolean

    suspend fun deleteSchedule(scheduleId: String): Boolean

    suspend fun applySchedule(scheduleId: String): Boolean
}