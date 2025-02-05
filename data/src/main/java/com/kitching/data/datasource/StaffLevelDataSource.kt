package com.kitching.data.datasource

import com.kitching.data.dto.StaffLevelDTO

interface StaffLevelDataSource {
    suspend fun getStaffLevel(staffLevelId: String): StaffLevelDTO?

    suspend fun getStaffLevels(teamId: String): List<StaffLevelDTO>

    suspend fun createStaffLevel(teamId: String, staffLevelName: String): Boolean

    suspend fun updateStaffLevel(staffLevelId: String, name: String): Boolean

    suspend fun deleteStaffLevel(staffLevelId: String): Boolean
}