package com.kitching.app.ui.model

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.kitching.app.util.PreferencesDataStore
import com.kitching.domain.AppResult
import com.kitching.domain.entities.Team
import com.kitching.domain.repository.LoginRepository
import com.kitching.domain.repository.TeamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val teamRepository: TeamRepository,
    val dataStore: PreferencesDataStore,
) : ViewModel() {
    private val _loginState = MutableStateFlow<AppResult<Unit>>(AppResult.Initial)
    val loginState: StateFlow<AppResult<Unit>> = _loginState

    suspend fun performKakaoLogin(context: Activity) {
        _loginState.value = AppResult.Loading
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                handleKakaoLoginResult(token, error)
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                handleKakaoLoginResult(token, error)
            }
        }
    }

    private fun handleKakaoLoginResult(token: OAuthToken?, error: Throwable?) {
        if (error != null) {
            _loginState.value = AppResult.Failure(error)
        } else if (token != null) {
            fetchKakaoUserInfo()
        }
    }

    private fun fetchKakaoUserInfo() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                _loginState.value = AppResult.Failure(error)
            } else if (user != null) {
                val kakaoUid = user.id.toString()
                val kakaoNickname = user.kakaoAccount?.profile?.nickname.orEmpty()
                val kakaoProfileImage = user.kakaoAccount?.profile?.profileImageUrl.orEmpty()

                viewModelScope.launch {
                    loginRepository.checkAndSaveUser(kakaoUid, kakaoNickname, kakaoProfileImage)
                    dataStore.saveUserId(kakaoUid)
                    _loginState.value = AppResult.Success(Unit)
                }
            }
        }
    }

    private val _teamList = MutableStateFlow<AppResult<List<Team>>>(AppResult.Initial)
    val teamList get() = _teamList.asStateFlow()

    fun getTeamList(userId: String) {
        viewModelScope.launch {
            teamRepository.getTeamsByUserId(userId).collectLatest {
                _teamList.value = it
            }
        }
    }

    private val _createTeamResult = MutableStateFlow<AppResult<Boolean>>(AppResult.Initial)
    val createTeamResult get() = _createTeamResult.asStateFlow()

    fun createTeam(ownerId: String, teamName: String, teamAmount: Int) {
        viewModelScope.launch {
            teamRepository.createTeam(ownerId, teamName, teamAmount).collectLatest { result ->
                _createTeamResult.value = result
            }
        }
    }
}