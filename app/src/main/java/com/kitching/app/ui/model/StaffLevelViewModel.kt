package com.kitching.app.ui.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitching.domain.AppResult
import com.kitching.domain.entities.StaffLevel
import com.kitching.domain.repository.StaffLevelRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class StaffLevelViewModel(
    private val repository: StaffLevelRepository
) : ViewModel() {

    private val _staffLevelList = MutableStateFlow<AppResult<List<StaffLevel>>>(AppResult.Loading)
    val staffLevelList get() = _staffLevelList.asStateFlow()

    fun getStaffLevelList(teamId: String) {
        viewModelScope.launch {
            repository.getStaffLevels(teamId).collectLatest {
                _staffLevelList.value = it
            }
        }
    }

    private val _staffLevelResult = MutableStateFlow<AppResult<Boolean>>(AppResult.Loading)
    val staffLevelResult get() = _staffLevelResult.asStateFlow()

    fun createStaffLevel(teamId: String, name: String) {
        viewModelScope.launch {
            repository.createStaffLevel(teamId, name).collectLatest {
                _staffLevelResult.value = it
            }
        }
    }

    fun updateStaffLevel(staffLevelId: String, name: String) {
        viewModelScope.launch {
            repository.updateStaffLevel(staffLevelId, name).collectLatest {
                _staffLevelResult.value = it
            }
        }
    }

    fun deleteStaffLevel(staffLevelId: String) {
        viewModelScope.launch {
            repository.deleteStaffLevel(staffLevelId).collectLatest {
                _staffLevelResult.value = it
            }
        }
    }
}