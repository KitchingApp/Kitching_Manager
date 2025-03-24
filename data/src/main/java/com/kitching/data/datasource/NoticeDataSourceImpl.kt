package com.kitching.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.data.dto.NoticeDTO
import com.kitching.data.exception.FailedCRUDInFirebaseException
import com.kitching.data.firebase.COLLECTION_NOTICE
import kotlinx.coroutines.tasks.await
import java.time.LocalDate

class NoticeDataSourceImpl(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) :
    NoticeDataSource {
    override suspend fun getNotices(teamId: String): List<NoticeDTO> =
        runCatching {
            db.collection(COLLECTION_NOTICE)
                .whereEqualTo("teamId", teamId)
                .get()
                .await()
                .toObjects(NoticeDTO::class.java)
        }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }

    override suspend fun createNotice(
        userId: String,
        teamId: String,
        title: String,
        content: String
    ) = runCatching {
        val docRef = db.collection(COLLECTION_NOTICE).add(
            NoticeDTO(
                id = "",
                writerId = userId,
                teamId = teamId,
                title = title,
                content = content,
                date = LocalDate.now().toString()
            )
        ).await()

        docRef.update("id", docRef.id)

        Unit
    }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }

    override suspend fun updateNotice(noticeId: String, title: String, content: String) =
        runCatching {
            db.collection(COLLECTION_NOTICE).document(noticeId)
                .update("title", title, "content", content).await()

            Unit
        }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }

    override suspend fun deleteNotice(noticeId: String) = runCatching {
        db.collection(COLLECTION_NOTICE).document(noticeId).delete().await()

        Unit
    }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }
}