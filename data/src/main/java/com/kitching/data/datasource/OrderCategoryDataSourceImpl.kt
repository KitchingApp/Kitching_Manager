package com.kitching.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.data.dto.OrderCategoryDTO
import com.kitching.data.firebase.COLLECTION_ORDER_CATEGORY
import kotlinx.coroutines.tasks.await

class OrderCategoryDataSourceImpl(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()):
    OrderCategoryDataSource {
    override suspend fun getOrderCategories(teamId: String): List<OrderCategoryDTO> {
            return db.collection(COLLECTION_ORDER_CATEGORY).whereEqualTo("teamId", teamId).get().await().toObjects(OrderCategoryDTO::class.java)
    }

    override suspend fun createOrderCategory(teamId: String, categoryName: String, color: String): Boolean {
        return runCatching {
            db.collection(COLLECTION_ORDER_CATEGORY).add(
                OrderCategoryDTO(
                    id = "",
                    name = categoryName,
                    color = color,
                    teamId = teamId
                )
            ).await().apply {
                this.update("id", this.id).await()
            }
        }.isSuccess
    }

    override suspend fun deleteOrderCategory(categoryId: String): Boolean {
        return runCatching {
            db.collection(COLLECTION_ORDER_CATEGORY).document(categoryId).delete().await()
        }.isSuccess
    }

    override suspend fun updateOrderCategory(
        categoryId: String,
        categoryName: String,
        color: String
    ): Boolean {
        return runCatching {
            db.collection(COLLECTION_ORDER_CATEGORY).document(categoryId).update("name", categoryName, "color", color)
        }.isSuccess
    }
}