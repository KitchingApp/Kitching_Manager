package com.kitching.data.datasource

import com.kitching.data.dto.NoticeDTO

interface NoticeDataSource {
    suspend fun getNotices(teamId: String): List<NoticeDTO>

    suspend fun createNotice(userId: String, teamId: String, title: String, content: String)

    suspend fun updateNotice(noticeId: String, title: String, content: String)

    suspend fun deleteNotice(noticeId: String)
}