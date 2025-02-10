package com.kitching.domain.repository

import com.kitching.domain.AppResult
import com.kitching.domain.entities.Notice
import kotlinx.coroutines.flow.Flow

interface NoticeRepository {
    fun getNotices(teamId: String): Flow<AppResult<List<Notice>>>

    fun createNotice(userId: String, teamId: String, title: String, content: String): Flow<AppResult<Boolean>>

    fun updateNotice(noticeId: String,title: String, content: String): Flow<AppResult<Boolean>>

    fun deleteNotice(noticeId: String): Flow<AppResult<Boolean>>

    fun getUserName(userId: String): Flow<AppResult<String>>
}