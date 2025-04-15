package com.kitching.app.ui.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitching.app.util.PreferencesDataStore
import com.kitching.domain.AppResult
import com.kitching.domain.entities.Team
import com.kitching.domain.entities.User
import com.kitching.domain.repository.TeamRepository
import com.kitching.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _user = MutableStateFlow<AppResult<User>>(AppResult.Initial)
    val user = _user.asStateFlow()

    fun getUser(userId: String) {
        viewModelScope.launch {
            userRepository.getUser(userId).collectLatest {
                _user.value = it
            }
        }
    }
}