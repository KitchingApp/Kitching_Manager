package com.kitching.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.data.dto.StaffLevelDTO
import com.kitching.data.exception.FailedCRUDInFirebaseException
import com.kitching.data.exception.StaffLevelNotFoundException
import com.kitching.data.firebase.COLLECTION_STAFF_LEVEL
import kotlinx.coroutines.tasks.await

class StaffLevelDataSourceImpl(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) :
    StaffLevelDataSource {
    override suspend fun getStaffLevel(staffLevelId: String) =
        runCatching {
            db.collection(COLLECTION_STAFF_LEVEL).document(staffLevelId).get().await()
                .toObject(StaffLevelDTO::class.java) ?: throw StaffLevelNotFoundException(
                staffLevelId
            )
        }.getOrElse {
            throw if (it is StaffLevelNotFoundException) it.getException()
            else FailedCRUDInFirebaseException(it).getException()
        }

    override suspend fun getStaffLevels(teamId: String): List<StaffLevelDTO> =
        runCatching {
            db.collection(COLLECTION_STAFF_LEVEL).whereEqualTo("teamId", teamId)
                .get().await()
                .toObjects(StaffLevelDTO::class.java)
        }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }

    override suspend fun createStaffLevel(teamId: String, staffLevelName: String) = runCatching {
        val docRef = db.collection(COLLECTION_STAFF_LEVEL).add(
            StaffLevelDTO(
                id = "",
                teamId = teamId,
                name = staffLevelName
            )
        ).await()

        docRef.update("id", docRef.id).await()

        Unit
    }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }

    override suspend fun updateStaffLevel(staffLevelId: String, name: String) = runCatching {
        db.collection(COLLECTION_STAFF_LEVEL).document(staffLevelId).update("name", name)
            .await()

        Unit
    }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }

    override suspend fun deleteStaffLevel(staffLevelId: String) = runCatching {
        db.collection(COLLECTION_STAFF_LEVEL).document(staffLevelId).delete().await()

        Unit
    }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }
}