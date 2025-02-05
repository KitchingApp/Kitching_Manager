package com.kitching.domain.repository

import com.kitching.domain.AppResult
import com.kitching.domain.entities.PrepCategory
import kotlinx.coroutines.flow.Flow

interface PrepCategoryRepository {
    suspend fun getPrepCategory(teamId: String): Flow<AppResult<List<PrepCategory>>>

    suspend fun createPrepCategory(teamId: String, categoryName: String, color: String): Flow<AppResult<Boolean>>

    suspend fun updatePrepCategory(categoryId: String, categoryName: String, color: String): Flow<AppResult<Boolean>>

    suspend fun deletePrepCategory(scheduleId: String): Flow<AppResult<Boolean>>
}