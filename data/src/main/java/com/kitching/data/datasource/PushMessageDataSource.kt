package com.kitching.data.datasource

import com.kitching.data.dto.NoticeMessageRes
import com.kitching.data.dto.ScheduleRejectPushMsgRes
import retrofit2.Response

interface PushMessageDataSource {
    suspend fun sendRejectPushMessage(
        teamName: String,
        scheduleDate: String,
        scheduleTimeName: String,
        rejectReason: String,
        registrationToken: String
    ): Response<ScheduleRejectPushMsgRes>

    suspend fun sendNoticePushMessage(
        title: String,
        writerName: String,
        content: String,
        fcmTokens: List<String>
    ): Response<NoticeMessageRes>
}