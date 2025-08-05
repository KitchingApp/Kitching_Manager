package com.kitching.data.dto

data class NoticeMessageReq(
    val title: String,
    val writerName: String,
    val content: String,
    val fcmTokens: List<String>
)
