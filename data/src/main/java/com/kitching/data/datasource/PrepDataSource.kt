package com.kitching.data.datasource

import com.kitching.data.dto.PrepDTO

interface PrepDataSource {
    suspend fun getPrepList(categoryId: String): List<PrepDTO>

    suspend fun createPrepList(categoryId: String, name: String): Boolean

    suspend fun updatePrepList(prepId: String, name: String): Boolean

    suspend fun deletePrepList(prepId: String): Boolean
}