package com.kitching.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.data.dto.OrderCategoryDTO
import com.kitching.data.exception.FailedCRUDInFirebaseException
import com.kitching.data.firebase.COLLECTION_ORDER_CATEGORY
import kotlinx.coroutines.tasks.await

class OrderCategoryDataSourceImpl(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) :
    OrderCategoryDataSource {
    override suspend fun getOrderCategories(teamId: String): List<OrderCategoryDTO>
    = runCatching {
        db.collection(COLLECTION_ORDER_CATEGORY).whereEqualTo("teamId", teamId).get().await().toObjects(OrderCategoryDTO::class.java)
    }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }

    override suspend fun createOrderCategory(teamId: String, categoryName: String, color: String) =
        runCatching {
            val docRef = db.collection(COLLECTION_ORDER_CATEGORY).add(
                OrderCategoryDTO(
                    id = "",
                    name = categoryName,
                    color = color,
                    teamId = teamId
                )
            ).await()

            docRef.update("id", docRef.id).await()

            Unit
        }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }

    override suspend fun deleteOrderCategory(categoryId: String) = runCatching {
        db.collection(COLLECTION_ORDER_CATEGORY).document(categoryId).delete().await()

        Unit
    }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }

    override suspend fun updateOrderCategory(
        categoryId: String,
        categoryName: String,
        color: String
    ) = runCatching {
        db.collection(COLLECTION_ORDER_CATEGORY).document(categoryId)
            .update("name", categoryName, "color", color)

        Unit
    }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }
}