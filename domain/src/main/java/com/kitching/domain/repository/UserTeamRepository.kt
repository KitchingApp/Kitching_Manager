package com.kitching.domain.repository

import com.kitching.domain.AppResult
import com.kitching.domain.entities.Member
import kotlinx.coroutines.flow.Flow

interface UserTeamRepository {
    fun getAllMembers(teamId: String): Flow<AppResult<List<Member>>>

    fun updateMember(
        userTeamId: String,
        staffLevelId: String,
        manager: Boolean
    ): Flow<AppResult<Boolean>>

}