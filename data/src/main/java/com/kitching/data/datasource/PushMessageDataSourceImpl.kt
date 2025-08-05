package com.kitching.data.datasource

import com.kitching.data.FirebaseFunctionApiService
import com.kitching.data.dto.NoticeMessageReq
import com.kitching.data.dto.ScheduleRejectPushMsgReq

class PushMessageDataSourceImpl(
    private val firebaseFunctionApiService: FirebaseFunctionApiService = FirebaseFunctionApiService.getInstance()
): PushMessageDataSource {
    override suspend fun sendRejectPushMessage(
        teamName: String,
        scheduleDate: String,
        scheduleTimeName: String,
        rejectReason: String,
        registrationToken: String
    ) = firebaseFunctionApiService.sendScheduleRejectMessage(ScheduleRejectPushMsgReq(
        teamName = teamName,
        scheduleDate = scheduleDate,
        scheduleTimeName = scheduleTimeName,
        rejectReason = rejectReason,
        registrationToken = registrationToken
    ))

    override suspend fun sendNoticePushMessage(
        title: String,
        writerName: String,
        content: String,
        fcmTokens: List<String>,
    ) = firebaseFunctionApiService.sendNoticeMessage(NoticeMessageReq(
        title = title,
        writerName = writerName,
        content = content,
        fcmTokens = fcmTokens
    ))
}