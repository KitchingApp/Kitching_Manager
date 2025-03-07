package com.kitching.data.datasource

import com.kitching.data.dto.FcmTokenDTO

interface FcmTokenDataSource {
    suspend fun getTokens(userId: String): List<FcmTokenDTO>
}