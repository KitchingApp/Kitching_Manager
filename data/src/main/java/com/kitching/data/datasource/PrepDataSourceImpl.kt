package com.kitching.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.data.dto.PrepDTO
import com.kitching.data.firebase.COLLECTION_PREP
import kotlinx.coroutines.tasks.await

class PrepDataSourceImpl(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) :
    PrepDataSource {
    override suspend fun getPrepList(categoryId: String): List<PrepDTO> {
        return db.collection(COLLECTION_PREP).whereEqualTo("categoryId", categoryId).get().await()
            .toObjects(PrepDTO::class.java)
    }

    override suspend fun createPrepList(categoryId: String, name: String) = runCatching {
        db.collection(COLLECTION_PREP).add(
            PrepDTO(
                categoryId = categoryId,
                id = "",
                name = name
            )
        ).await().apply {
            update("id", this.id).await()
        }
    }.isSuccess

    override suspend fun updatePrepList(prepId: String, name: String) = runCatching {
        db.collection(COLLECTION_PREP).document(prepId).update("name", name).await()
    }.isSuccess

    override suspend fun deletePrepList(prepId: String) = runCatching {
        db.collection(COLLECTION_PREP).document(prepId).delete().await()
    }.isSuccess
}