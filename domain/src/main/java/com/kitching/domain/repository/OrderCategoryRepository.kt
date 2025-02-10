package com.kitching.domain.repository

import com.kitching.domain.AppResult
import com.kitching.domain.entities.OrderCategory
import kotlinx.coroutines.flow.Flow

interface OrderCategoryRepository {
    fun getOrderCategory(teamId: String): Flow<AppResult<List<OrderCategory>>>

    fun createOrderCategory(teamId: String, categoryName: String, color: String): Flow<AppResult<Boolean>>

    fun deleteOrderCategory(categoryId: String): Flow<AppResult<Boolean>>

    fun updateOrderCategory(categoryId: String, categoryName: String, color: String): Flow<AppResult<Boolean>>
}