package com.kitching.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.data.dto.PrepCategoryDTO
import com.kitching.data.exception.FailedCRUDInFirebaseException
import com.kitching.data.firebase.COLLECTION_PREP_CATEGORY
import kotlinx.coroutines.tasks.await

class PrepCategoryDataSourceImpl(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) :
    PrepCategoryDataSource {
    override suspend fun getPrepCategory(teamId: String): List<PrepCategoryDTO> =
        runCatching {
            db.collection(COLLECTION_PREP_CATEGORY).whereEqualTo("teamId", teamId).get().await()
                .toObjects(PrepCategoryDTO::class.java)
        }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }

    override suspend fun createPrepCategory(
        teamId: String,
        categoryName: String,
        color: String,
    ) = runCatching {
        val docRef = db.collection(COLLECTION_PREP_CATEGORY).add(
            PrepCategoryDTO(
                id = "",
                teamId = teamId,
                name = categoryName,
                color = color
            )
        ).await()

        docRef.update("id", docRef.id).await()

        Unit
    }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }


    override suspend fun updatePrepCategory(
        categoryId: String,
        categoryName: String,
        color: String,
    ) = runCatching {
        db.collection(COLLECTION_PREP_CATEGORY).document(categoryId)
            .update("name", categoryName, "color", color)

        Unit
    }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }

    override suspend fun deletePrepCategory(prepCategoryId: String) = runCatching {
        db.collection(COLLECTION_PREP_CATEGORY).document(prepCategoryId).delete().await()

        Unit
    }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }
}