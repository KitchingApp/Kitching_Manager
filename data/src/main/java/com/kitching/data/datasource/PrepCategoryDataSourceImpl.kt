package com.kitching.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.data.dto.PrepCategoryDTO
import com.kitching.data.firebase.COLLECTION_PREP_CATEGORY
import kotlinx.coroutines.tasks.await

class PrepCategoryDataSourceImpl(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) :
    PrepCategoryDataSource {
    override suspend fun getPrepCategory(teamId: String): List<PrepCategoryDTO> =
        db.collection(COLLECTION_PREP_CATEGORY).whereEqualTo("teamId", teamId).get().await()
            .toObjects(PrepCategoryDTO::class.java)

    override suspend fun createPrepCategory(
        teamId: String,
        categoryName: String,
        color: String,
    ) = runCatching {
        db.collection(COLLECTION_PREP_CATEGORY).add(
            PrepCategoryDTO(
                id = "",
                teamId = teamId,
                name = categoryName,
                color = color
            )
        ).await().apply {
            update("id", this.id).await()
        }
    }.isSuccess


    override suspend fun updatePrepCategory(
        categoryId: String,
        categoryName: String,
        color: String,
    ) = runCatching {
        db.collection(COLLECTION_PREP_CATEGORY).document(categoryId)
            .update("name", categoryName, "color", color)
    }.isSuccess

    override suspend fun deletePrepCategory(prepCategoryId: String) = runCatching {
        db.collection(COLLECTION_PREP_CATEGORY).document(prepCategoryId).delete().await()
    }.isSuccess
}