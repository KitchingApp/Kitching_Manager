package com.kitching.domain.repository

import com.kitching.domain.AppResult
import com.kitching.domain.entities.Member
import com.kitching.domain.entities.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUser(userId: String): Flow<AppResult<User>>
}