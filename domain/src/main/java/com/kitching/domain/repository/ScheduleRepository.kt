package com.kitching.domain.repository

import com.kitching.domain.AppResult
import com.kitching.domain.entities.Schedule
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    fun getSchedules(teamId: String, date: String): Flow<AppResult<List<Schedule>>>

    fun createSchedule(
        teamId: String,
        dateString: String,
        userId: String,
        scheduleTimeId: String,
        fix: Boolean = true
    ): Flow<AppResult<Unit>>

    fun deleteSchedule(scheduleId: String): Flow<AppResult<Unit>>

    fun applySchedule(scheduleId: String): Flow<AppResult<Unit>>
}