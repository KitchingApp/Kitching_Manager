package com.kitching.data.datasource

import com.kitching.data.dto.OrderCategoryDTO

interface OrderCategoryDataSource {
    suspend fun getOrderCategories(teamId: String): List<OrderCategoryDTO>

    suspend fun createOrderCategory(teamId: String, categoryName: String, color: String)

    suspend fun deleteOrderCategory(categoryId: String)

    suspend fun updateOrderCategory(categoryId: String, categoryName: String, color: String)
}