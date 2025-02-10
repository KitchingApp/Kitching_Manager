package com.kitching.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.data.dto.OrderDTO
import com.kitching.data.firebase.COLLECTION_ORDER
import kotlinx.coroutines.tasks.await

class OrderDataSourceImpl(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()):
    OrderDataSource {
    override suspend fun getOrderList(categoryId: String): List<OrderDTO> {
        return db.collection(COLLECTION_ORDER).whereEqualTo("categoryId", categoryId).get().await().toObjects(OrderDTO::class.java)
    }

    override suspend fun createOrder(categoryId: String, name: String): Boolean {
        return runCatching {
            db.collection(COLLECTION_ORDER).add(
                OrderDTO(
                    id = "",
                    name = name,
                    categoryId = categoryId
                )
            ).await().apply {
                this.update("id", this.id).await()
            }
        }.isSuccess
    }

    override suspend fun updateOrder(orderId: String, name: String): Boolean {
        return runCatching {
            db.collection(COLLECTION_ORDER).document(orderId).update("name", name).await()
        }.isSuccess
    }

    override suspend fun deleteOrder(orderId: String): Boolean {
        return runCatching {
            db.collection(COLLECTION_ORDER).document(orderId).delete().await()
        }.isSuccess
    }
}