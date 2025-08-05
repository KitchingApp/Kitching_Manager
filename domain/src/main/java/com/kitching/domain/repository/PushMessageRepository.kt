package com.kitching.domain.repository

import com.kitching.domain.AppResult
import com.kitching.domain.entities.Schedule
import kotlinx.coroutines.flow.Flow

interface PushMessageRepository {
    fun sendRejectPushMessage(
        teamId: String,
        schedule: Schedule,
        rejectReason: String
    ): Flow<AppResult<Boolean>>

    fun sendNoticeMessage(
        title: String,
        userId: String,
        teamId: String,
        content: String,
    ): Flow<AppResult<String?>>
}