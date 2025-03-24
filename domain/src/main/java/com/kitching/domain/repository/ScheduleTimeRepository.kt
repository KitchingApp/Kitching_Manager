package com.kitching.domain.repository

import com.kitching.domain.AppResult
import com.kitching.domain.entities.ScheduleTime
import kotlinx.coroutines.flow.Flow

interface ScheduleTimeRepository {
    fun getScheduleTimes(teamId: String): Flow<AppResult<List<ScheduleTime>>>

    fun createScheduleTime(teamId: String, name: String, startTime: String, endTime: String): Flow<AppResult<Unit>>

    fun updateScheduleTime(scheduleTimeId: String, name: String, startTime: String, endTime: String): Flow<AppResult<Unit>>

    fun deleteScheduleTime(scheduleTimeId: String): Flow<AppResult<Unit>>
}