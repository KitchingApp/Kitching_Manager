package com.kitching.data.datasource

import com.kitching.data.dto.OrderDTO

interface OrderDataSource {
    suspend fun getOrderList(categoryId: String): List<OrderDTO>

    suspend fun createOrder(categoryId: String, name: String): Boolean

    suspend fun updateOrder(orderId: String, name: String): Boolean

    suspend fun deleteOrder(orderId: String): Boolean
}