package com.kitching.domain.repository

import com.kitching.domain.AppResult
import com.kitching.domain.entities.Prep
import kotlinx.coroutines.flow.Flow

interface PrepRepository {
    fun getPrepList(categoryId: String): Flow<AppResult<List<Prep>>>

    fun createPrep(categoryId: String, name: String): Flow<AppResult<Boolean>>

    fun updatePrep(prepId: String, name: String): Flow<AppResult<Boolean>>

    fun deletePrep(prepId: String): Flow<AppResult<Boolean>>
}