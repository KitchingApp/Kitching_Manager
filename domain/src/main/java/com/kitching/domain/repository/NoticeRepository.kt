package com.kitching.domain.repository

import com.kitching.domain.AppResult
import com.kitching.domain.entities.Notice
import kotlinx.coroutines.flow.Flow

interface NoticeRepository {
    fun getNotices(teamId: String): Flow<AppResult<List<Notice>>>

    fun createNotice(userId: String, teamId: String, title: String, content: String): Flow<AppResult<Unit>>

    fun updateNotice(noticeId: String,title: String, content: String): Flow<AppResult<Unit>>

    fun deleteNotice(noticeId: String): Flow<AppResult<Unit>>

    fun getUserName(userId: String): Flow<AppResult<String>>
}