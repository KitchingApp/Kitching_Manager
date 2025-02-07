package com.kitching.app.ui.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitching.domain.AppResult
import com.kitching.domain.entities.ScheduleTime
import com.kitching.domain.repository.ScheduleTimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ScheduleTimeViewModel(private val repository: ScheduleTimeRepository) : ViewModel() {
    private var _scheduleTimes =
        MutableStateFlow<AppResult<List<ScheduleTime>>>(AppResult.Initial)
    val scheduleTimes get() = _scheduleTimes.asStateFlow()

    fun getScheduleTimes(teamId: String) {
        viewModelScope.launch {
            repository.getScheduleTimes(teamId).collectLatest {
                _scheduleTimes.value = it
            }
        }
    }

    private var _scheduleTimeResult =
        MutableStateFlow<AppResult<Boolean>>(AppResult.Initial)
    val scheduleTimeResult get() = _scheduleTimeResult.asStateFlow()

    fun createScheduleTime(
        teamId: String,
        name: String,
        startTime: String,
        endTime: String
    ) {
        viewModelScope.launch {
            repository.createScheduleTime(teamId, name, startTime, endTime).collectLatest {
                _scheduleTimeResult.value = it
            }
        }
    }

    fun updateScheduleTime(
        scheduleTimeId: String,
        name: String,
        startTime: String,
        endTime: String
    ) {
        viewModelScope.launch {
            repository.updateScheduleTime(scheduleTimeId, name, startTime, endTime)
                .collectLatest {
                    _scheduleTimeResult.value = it
                }
        }
    }

    fun deleteScheduleTime(scheduleTimeId: String) {
        viewModelScope.launch {
            repository.deleteScheduleTime(scheduleTimeId).collectLatest {
                _scheduleTimeResult.value = it
            }
        }
    }
}