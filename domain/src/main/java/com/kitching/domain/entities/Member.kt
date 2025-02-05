package com.kitching.domain.entities

data class Member(
    val userId: String,
    val userName: String,
    val staffLevelId: String,
    val staffLevelName: String?,
    val manager: Boolean
)
