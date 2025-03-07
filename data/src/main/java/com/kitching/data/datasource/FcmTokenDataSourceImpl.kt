package com.kitching.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.data.dto.FcmTokenDTO
import com.kitching.data.firebase.COLLECTION_FIREBASE_MESSAGING_TOKEN
import kotlinx.coroutines.tasks.await

class FcmTokenDataSourceImpl(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) :
    FcmTokenDataSource {
    override suspend fun getTokens(userId: String): List<FcmTokenDTO> =
        db.collection(COLLECTION_FIREBASE_MESSAGING_TOKEN)
            .whereEqualTo("userId", userId)
            .get()
            .await()
            .toObjects(FcmTokenDTO::class.java)
}