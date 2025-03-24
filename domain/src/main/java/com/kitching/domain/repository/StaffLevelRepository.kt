package com.kitching.domain.repository

import com.kitching.domain.AppResult
import com.kitching.domain.entities.StaffLevel
import kotlinx.coroutines.flow.Flow

interface StaffLevelRepository {
    fun getStaffLevels(teamId: String): Flow<AppResult<List<StaffLevel>>>

    fun createStaffLevel(teamId: String, name: String): Flow<AppResult<Unit>>

    fun updateStaffLevel(staffLevelId: String, name: String): Flow<AppResult<Unit>>

    fun deleteStaffLevel(staffLevelId: String): Flow<AppResult<Unit>>
}