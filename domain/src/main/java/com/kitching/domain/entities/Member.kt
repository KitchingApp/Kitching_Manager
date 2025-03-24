package com.kitching.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class Member(
    val userTeamId: String,
    val userId: String,
    val userName: String,
    val userImage: String,
    val staffLevelId: String,
    val staffLevelName: String,
    val manager: Boolean
) {
    companion object {
        fun init() = Member(
            userTeamId = "",
            userId = "",
            userName = "",
            userImage = "",
            staffLevelId = "",
            staffLevelName = "",
            manager = false
        )
    }
}
