package com.kitching.app.ui.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitching.domain.AppResult
import com.kitching.domain.entities.Member
import com.kitching.domain.entities.StaffLevel
import com.kitching.domain.entities.Team
import com.kitching.domain.repository.TeamRepository
import com.kitching.domain.repository.UserTeamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class InviteCodeViewModel(
    private val teamRepository: TeamRepository
) : ViewModel() {

    private val _team = MutableStateFlow<AppResult<Team>>(AppResult.Initial)
    val team get() = _team.asStateFlow()

    fun getTeam(teamId: String) {
        viewModelScope.launch {
            teamRepository.getTeam(teamId).collectLatest {
                _team.value = it
            }
        }
    }
}