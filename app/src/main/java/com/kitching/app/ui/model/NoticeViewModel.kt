package com.kitching.app.ui.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitching.domain.AppResult
import com.kitching.domain.entities.Notice
import com.kitching.domain.repository.NoticeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NoticeViewModel(
    private val noticeRepository: NoticeRepository
) : ViewModel() {

    private val _notices = MutableStateFlow<AppResult<List<Notice>>>(AppResult.Initial)
    val notices get() = _notices.asStateFlow()

    fun getNotices(teamId: String) {
        viewModelScope.launch {
            noticeRepository.getNotices(teamId).collectLatest {
                _notices.value = it
            }
        }
    }

    private val _noticeResult = MutableStateFlow<AppResult<Unit>>(AppResult.Initial)
    val noticeResult get() = _noticeResult.asStateFlow()

    fun createNotice(userId: String, teamId: String, title: String, content: String) {
        viewModelScope.launch {
            noticeRepository.createNotice(userId, teamId, title, content).collectLatest {
                _noticeResult.value = it
            }
        }
    }

    fun updateNotice(noticeId: String, title: String, content: String) {
        viewModelScope.launch {
            noticeRepository.updateNotice(noticeId, title, content).collectLatest {
                _noticeResult.value = it
            }
        }
    }

    fun deleteNotice(noticeId: String) {
        viewModelScope.launch {
            noticeRepository.deleteNotice(noticeId).collectLatest {
                _noticeResult.value = it
            }
        }
    }

    private val _userName = MutableStateFlow<AppResult<String>>(AppResult.Initial)
    val userName get() = _userName.asStateFlow()

    fun getUserName(userId: String) {
        viewModelScope.launch {
            noticeRepository.getUserName(userId).collectLatest {
                _userName.value = it
            }
        }
    }
}