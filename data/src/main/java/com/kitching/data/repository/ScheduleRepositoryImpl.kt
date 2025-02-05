package com.kitching.data.repository

import com.kitching.data.datasource.ScheduleDataSource
import com.kitching.data.datasource.ScheduleDataSourceImpl
import com.kitching.data.datasource.ScheduleTimeDataSource
import com.kitching.data.datasource.ScheduleTimeDataSourceImpl
import com.kitching.data.datasource.UserDataSource
import com.kitching.data.datasource.UserDataSourceImpl
import com.kitching.domain.AppResult
import com.kitching.domain.entities.Schedule
import com.kitching.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class ScheduleRepositoryImpl(
    private val scheduleDataSource: ScheduleDataSource = ScheduleDataSourceImpl(),
    private val scheduleTimeDataSource: ScheduleTimeDataSource = ScheduleTimeDataSourceImpl(),
    private val userDataSource: UserDataSource = UserDataSourceImpl()
) : ScheduleRepository {
    override fun getSchedules(
        teamId: String,
        date: String
    ) = flow {
        emit(AppResult.Loading)
        val schedules = scheduleDataSource.getSchedules(teamId, date).map {
            val userName = userDataSource.getUser(it.userId)
            val scheduleTimeName = scheduleTimeDataSource.getScheduleTime(it.scheduleTimeId)
            if (userName !== null && scheduleTimeName !== null) {
                Schedule(
                    scheduleId = it.id,
                    userId = it.userId,
                    userName = userName.userName,
                    scheduleTimeName = scheduleTimeName.name,
                    date = it.date,
                    fix = it.fix,
                )
            } else if(userName == null) {
                throw Throwable("userName Not Found")
            } else {
                throw Throwable("scheduleTime Name Not Found")
            }
        }
        if (schedules.isEmpty()) emit(AppResult.Success(emptyList()))
        else emit(AppResult.Success(schedules))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override fun createSchedule(
        teamId: String,
        dateString: String,
        userId: String,
        scheduleTimeId: String,
        fix: Boolean
    ) = flow {
        emit(AppResult.Loading)
        emit(
            AppResult.Success(
                scheduleDataSource.createSchedule(
                    teamId,
                    dateString,
                    userId,
                    scheduleTimeId,
                    fix
                )
            )
        )
    }.catch {
        emit(AppResult.Failure(it))
    }

    override fun deleteSchedule(scheduleId: String) = flow {
        emit(AppResult.Loading)
        emit(AppResult.Success(scheduleDataSource.deleteSchedule(scheduleId)))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override fun applySchedule(scheduleId: String) = flow {
        emit(AppResult.Loading)
        emit(AppResult.Success(scheduleDataSource.applySchedule(scheduleId)))
    }.catch {
        emit(AppResult.Failure(it))
    }
}