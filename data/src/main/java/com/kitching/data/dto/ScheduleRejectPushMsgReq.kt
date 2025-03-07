package com.kitching.data.dto

data class ScheduleRejectPushMsgReq(
    val teamName: String,
    val scheduleDate: String,
    val scheduleTimeName: String,
    val rejectReason: String,
    val registrationToken: String
)