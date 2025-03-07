package com.kitching.data.datasource

import com.kitching.data.FirebaseFunctionApiService
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
    ) = firebaseFunctionApiService.sendPushMessage(ScheduleRejectPushMsgReq(
        teamName = teamName,
        scheduleDate = scheduleDate,
        scheduleTimeName = scheduleTimeName,
        rejectReason = rejectReason,
        registrationToken = registrationToken
    ))
}