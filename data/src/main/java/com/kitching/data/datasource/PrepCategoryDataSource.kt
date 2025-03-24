package com.kitching.data.datasource

import com.kitching.data.dto.PrepCategoryDTO

interface PrepCategoryDataSource {
    suspend fun getPrepCategory(teamId: String): List<PrepCategoryDTO>

    suspend fun createPrepCategory(teamId: String, categoryName: String, color: String)

    suspend fun updatePrepCategory(categoryId: String, categoryName: String, color: String)

    suspend fun deletePrepCategory(prepCategoryId: String)
}