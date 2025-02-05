package com.kitching.app.ui.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitching.domain.AppResult
import com.kitching.domain.entities.Member
import com.kitching.domain.entities.Schedule
import com.kitching.domain.entities.ScheduleTime
import com.kitching.domain.repository.ScheduleRepository
import com.kitching.domain.repository.ScheduleTimeRepository
import com.kitching.domain.repository.UserTeamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ScheduleViewModel(
    private val scheduleTimeRepository: ScheduleTimeRepository,
    private val userTeamRepository: UserTeamRepository,
    private val scheduleRepository: ScheduleRepository
) :
    ViewModel() {
    private val _schedules = MutableStateFlow<AppResult<List<Schedule>>>(AppResult.Initial)
    val schedules get() = _schedules.asStateFlow()

    fun getSchedules(teamId: String, dateString: String) {
        viewModelScope.launch {
            scheduleRepository.getSchedules(teamId, dateString).collectLatest {
                _schedules.value = it
                Log.d("scheduleviewmodel", dateString)
                Log.d("scheduleviewmodel", it.toString())
            }
        }
    }

    private val _scheduleResult =
        MutableStateFlow<AppResult<Boolean>>(AppResult.Initial)
    val scheduleResult get() = _scheduleResult.asStateFlow()

    fun createSchedule(
        teamId: String,
        dateString: String,
        userId: String,
        scheduleTimeId: String,
        fix: Boolean = true
    ) {
        viewModelScope.launch {
            scheduleRepository.createSchedule(teamId, dateString, userId, scheduleTimeId, fix)
                .collectLatest {
                    _scheduleResult.value = it
                }
        }
    }

    fun applySchedule(scheduleId: String) {
        viewModelScope.launch {
            scheduleRepository.applySchedule(scheduleId)
                .collectLatest { _scheduleResult.value = it }
        }
    }

    fun deleteSchedule(scheduleId: String, isReject: Boolean = false) {
        viewModelScope.launch {
            scheduleRepository.deleteSchedule(scheduleId)
                .collectLatest {
                    Log.d("deleteSchedule", "deleteSchedule: $scheduleId")
                    _scheduleResult.value = it
                }
        }
    }

    private val _members =
        MutableStateFlow<AppResult<List<Member>>>(AppResult.Initial)
    val members get() = _members.asStateFlow()

    fun getMembers(teamId: String) {
        viewModelScope.launch {
            userTeamRepository.getAllMembers(teamId).collectLatest { _members.value = it }
        }
    }

    private val _scheduleTimes =
        MutableStateFlow<AppResult<List<ScheduleTime>>>(AppResult.Initial)
    val scheduleTimes get() = _scheduleTimes.asStateFlow()

    fun getScheduleTimes(teamId: String) {
        viewModelScope.launch {
            scheduleTimeRepository.getScheduleTimes(teamId)
                .collectLatest { _scheduleTimes.value = it }
        }
    }
}