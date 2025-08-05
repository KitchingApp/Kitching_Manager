package com.kitching.app.ui.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitching.domain.AppResult
import com.kitching.domain.entities.Notice
import com.kitching.domain.repository.NoticeRepository
import com.kitching.domain.repository.PushMessageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NoticeViewModel(
    private val noticeRepository: NoticeRepository,
    private val pushMessageRepository: PushMessageRepository
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

    private val _createNoticeResult = MutableStateFlow<AppResult<String>>(AppResult.Initial)
    val createNoticeResult get() = _createNoticeResult.asStateFlow()

    fun createNotice(userId: String, teamId: String, title: String, content: String) {
        viewModelScope.launch {
            noticeRepository.createNotice(userId, teamId, title, content).collectLatest { result ->
                when (result) {
                    is AppResult.Initial -> {
                        _createNoticeResult.value = AppResult.Initial
                    }

                    is AppResult.Loading -> {
                        _createNoticeResult.value = AppResult.Loading
                    }

                    is AppResult.Success -> {
                        sendNoticePushMessage(title, userId, teamId, content)
                    }

                    is AppResult.Failure -> {
                        _createNoticeResult.value = AppResult.Failure(result.exception)
                    }
                }

            }
        }
    }

    private fun sendNoticePushMessage(title: String, userId: String, teamId: String, content: String) {
        viewModelScope.launch {
            pushMessageRepository.sendNoticeMessage(title, userId, teamId, content).collectLatest { result ->
                when (result) {
                    is AppResult.Initial -> {
                        _createNoticeResult.value = AppResult.Initial
                    }

                    is AppResult.Loading -> {
                        _createNoticeResult.value = AppResult.Loading
                    }

                    is AppResult.Success -> {
                        _createNoticeResult.value = AppResult.Success(result.data ?: "")
                    }

                    is AppResult.Failure -> {
                        _createNoticeResult.value = AppResult.Failure(result.exception)
                    }
                }
            }
        }
    }

    private val _noticeResult = MutableStateFlow<AppResult<Unit>>(AppResult.Initial)
    val noticeResult get() = _noticeResult.asStateFlow()

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

    fun resetNoticeResult() {
        _noticeResult.value = AppResult.Initial
        _createNoticeResult.value = AppResult.Initial
    }
}