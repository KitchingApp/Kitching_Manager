package com.kitching.app.ui.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitching.domain.AppResult
import com.kitching.domain.entities.Member
import com.kitching.domain.entities.Notice
import com.kitching.domain.entities.StaffLevel
import com.kitching.domain.repository.StaffLevelRepository
import com.kitching.domain.repository.UserTeamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MemberViewModel(
    private val userTeamRepository: UserTeamRepository,
    private val staffLevelRepository: StaffLevelRepository,
) : ViewModel() {

    private val _members = MutableStateFlow<AppResult<List<Member>>>(AppResult.Initial)
    val members get() = _members.asStateFlow()

    fun getMembers(teamId: String) {
        viewModelScope.launch {
            userTeamRepository.getAllMembers(teamId).collectLatest {
                _members.value = it
            }
        }
    }

    private val _memberResult = MutableStateFlow<AppResult<Unit>>(AppResult.Initial)
    val memberResult get() = _memberResult.asStateFlow()

    fun updateMember(userTeamId: String, staffLevelId: String, manager: Boolean) {
        viewModelScope.launch {
            userTeamRepository.updateMember(
                userTeamId = userTeamId,
                staffLevelId = staffLevelId,
                manager = manager
            ).collectLatest {
                _memberResult.value = it
            }
        }
    }

    private val _staffLevels = MutableStateFlow<AppResult<List<StaffLevel>>>(AppResult.Initial)
    val staffLevels get() = _staffLevels.asStateFlow()

    fun getStaffLevels(teamId: String) {
        viewModelScope.launch {
            staffLevelRepository.getStaffLevels(teamId).collectLatest {
                _staffLevels.value = it
            }
        }
    }
}