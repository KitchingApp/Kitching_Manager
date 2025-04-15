package com.kitching.domain.repository

import com.kitching.domain.AppResult
import com.kitching.domain.entities.Order
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getOrderList(categoryId: String): Flow<AppResult<List<Order>>>

    fun createOrder(categoryId: String, orderName: String): Flow<AppResult<Unit>>

    fun deleteOrder(orderId: String): Flow<AppResult<Unit>>

    fun updateOrder(orderId: String, orderName: String): Flow<AppResult<Unit>>
}