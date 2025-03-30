package com.kitching.domain.entities

data class Schedule(
    var scheduleId: String,
    val userId: String,
    val userName: String,
    val scheduleTimeName: String,
    val date: String,
    val fix: Boolean,
) {
    constructor() : this(
        scheduleId = "",
        userId = "",
        userName = "",
        scheduleTimeName = "",
        date = "",
        fix = false
    )
}