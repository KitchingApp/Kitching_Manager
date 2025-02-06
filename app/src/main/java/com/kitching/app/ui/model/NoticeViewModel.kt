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

class NoticeViewModel(private val repository: NoticeRepository) : ViewModel() {

    private val _notices = MutableStateFlow<AppResult<List<Notice>>>(AppResult.Loading)
    val notices get() = _notices.asStateFlow()

    fun getNotices(teamId: String) {
        viewModelScope.launch {
            repository.getNotices(teamId).collectLatest {
                _notices.value = it
            }
        }
    }

    private val _noticeResult = MutableStateFlow<AppResult<Boolean>>(AppResult.Success(true))
    val noticeResult get() = _noticeResult.asStateFlow()

    fun createNotice(userId: String, teamId: String, title: String, content: String) {
        viewModelScope.launch {
            repository.createNotice(userId, teamId, title, content).collectLatest {
                _noticeResult.value = it
            }
        }
    }

    fun updateNotice(noticeId: String, title: String, content: String) {
        viewModelScope.launch {
            repository.updateNotice(noticeId, title, content).collectLatest {
                _noticeResult.value = it
            }
        }
    }

    fun deleteNotice(noticeId: String) {
        viewModelScope.launch {
            repository.deleteNotice(noticeId).collectLatest {
                _noticeResult.value = it
            }
        }
    }
}