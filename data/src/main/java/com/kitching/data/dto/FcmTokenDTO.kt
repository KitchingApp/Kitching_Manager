package com.kitching.data.dto

import com.kitching.domain.entities.FcmToken

data class FcmTokenDTO(
    val id: String = "",
    val token: String = "",
    val deviceModel: String = "",
    val userId: String = ""
) {
    fun toDomain() = FcmToken(
        token = token,
        deviceModel = deviceModel
    )
}