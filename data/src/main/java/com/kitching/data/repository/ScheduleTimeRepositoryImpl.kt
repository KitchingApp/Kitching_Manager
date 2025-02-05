package com.kitching.data.repository

import com.kitching.data.datasource.ScheduleTimeDataSource
import com.kitching.data.datasource.ScheduleTimeDataSourceImpl
import com.kitching.domain.AppResult
import com.kitching.domain.entities.ScheduleTime
import com.kitching.domain.repository.ScheduleTimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.time.LocalTime

class ScheduleTimeRepositoryImpl(private val scheduleTimeDataSource: ScheduleTimeDataSource = ScheduleTimeDataSourceImpl()) :
    ScheduleTimeRepository {
    override fun getScheduleTimes(teamId: String): Flow<AppResult<List<ScheduleTime>>> =
        flow {
            emit(AppResult.Loading)
            val scheduleTimes = scheduleTimeDataSource.getScheduleTimes(teamId)
            if (scheduleTimes.isEmpty()) emit(AppResult.Success(emptyList()))
            else emit(AppResult.Success(scheduleTimes.map {
                ScheduleTime(
                    scheduleTimeId = it.id,
                    scheduleTimeName = it.name,
                    startTime = LocalTime.parse(it.startTime),
                    endTime = LocalTime.parse(it.endTime)
                )
            }))
        }.catch {
            emit(AppResult.Failure(it))
        }

    override fun createScheduleTime(
        teamId: String,
        name: String,
        color: String,
        startTime: String,
        endTime: String
    ) = flow {
        emit(AppResult.Loading)
        emit(
            AppResult.Success(
                scheduleTimeDataSource.createScheduleTime(
                    teamId,
                    name,
                    color,
                    startTime,
                    endTime
                )
            )
        )
    }.catch {
        emit(AppResult.Failure(it))
    }

    override fun updateScheduleTime(
        scheduleTimeId: String,
        name: String,
        color: String,
        startTime: String,
        endTime: String
    ) = flow {
        emit(AppResult.Loading)
        emit(
            AppResult.Success(
                scheduleTimeDataSource.updateScheduleTime(
                    scheduleTimeId,
                    name,
                    color,
                    startTime,
                    endTime
                )
            )
        )
    }.catch {
        emit(AppResult.Failure(it))
    }

    override fun deleteScheduleTime(scheduleTimeId: String) = flow {
        emit(AppResult.Loading)
        emit(AppResult.Success(scheduleTimeDataSource.deleteScheduleTime(scheduleTimeId)))
    }.catch {
        emit(AppResult.Failure(it))
    }

}