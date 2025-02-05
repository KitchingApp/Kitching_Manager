package com.kitching.domain.entities

import java.time.LocalDate

data class Notice(
    val noticeId: String,
    val writerName: String,
    val date: LocalDate,
    val title: String,
    val content: String,
)
