package com.kitching.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.data.dto.OrderDTO
import com.kitching.data.exception.FailedCRUDInFirebaseException
import com.kitching.data.firebase.COLLECTION_ORDER
import kotlinx.coroutines.tasks.await

class OrderDataSourceImpl(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()):
    OrderDataSource {
    override suspend fun getOrderList(categoryId: String): List<OrderDTO>
    = runCatching {
        db.collection(COLLECTION_ORDER).whereEqualTo("categoryId", categoryId).get().await().toObjects(OrderDTO::class.java)
    }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }

    override suspend fun createOrder(categoryId: String, name: String) = runCatching {
        val docRef = db.collection(COLLECTION_ORDER).add(
            OrderDTO(
                id = "",
                name = name,
                categoryId = categoryId
            )
        ).await()

        docRef.update("id", docRef.id).await()

        Unit
    }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }

    override suspend fun updateOrder(orderId: String, name: String) = runCatching {
        db.collection(COLLECTION_ORDER).document(orderId).update("name", name).await()

        Unit
    }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }

    override suspend fun deleteOrder(orderId: String) = runCatching {
        db.collection(COLLECTION_ORDER).document(orderId).delete().await()

        Unit
    }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }
}