package com.kitching.app.ui.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitching.app.util.PreferencesDataStore
import com.kitching.domain.AppResult
import com.kitching.domain.entities.Team
import com.kitching.domain.repository.TeamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TeamViewModel(
    private val teamRepository: TeamRepository,
    val dataStore: PreferencesDataStore,
) : ViewModel() {
    private val _teamList = MutableStateFlow<AppResult<List<Team>>>(AppResult.Initial)
    val teamList get() = _teamList.asStateFlow()

    fun getTeamList(userId: String) {
        viewModelScope.launch {
            teamRepository.getTeamsByUserId(userId).collectLatest {
                _teamList.value = it
            }
        }
    }

    private val _createTeamResult = MutableStateFlow<AppResult<String>>(AppResult.Initial)
    val createTeamResult get() = _createTeamResult.asStateFlow()

    fun createTeam(ownerId: String, teamName: String, teamAmount: Int) {
        viewModelScope.launch {
            teamRepository.createTeam(ownerId, teamName, teamAmount).collectLatest { result ->
                _createTeamResult.value = result
            }
        }
    }

    private val _team = MutableStateFlow<AppResult<Team>>(AppResult.Initial)
    var team = _team.asStateFlow()

    fun getTeam(teamId: String) {
        viewModelScope.launch {
            teamRepository.getTeam(teamId).collectLatest {
                _team.value = it
            }
        }
    }
}