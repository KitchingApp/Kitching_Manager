package com.kitching.data.datasource

import com.kitching.data.dto.ScheduleTimeDTO

interface ScheduleTimeDataSource {
    suspend fun getScheduleTime(scheduleTimeId: String): ScheduleTimeDTO?

    suspend fun getScheduleTimes(teamId: String): List<ScheduleTimeDTO>

    suspend fun createScheduleTime(teamId: String, name: String, startTime: String, endTime: String): Boolean

    suspend fun updateScheduleTime(scheduleTimeId: String, name: String, startTime: String, endTime: String): Boolean

    suspend fun deleteScheduleTime(scheduleTimeId: String): Boolean
}