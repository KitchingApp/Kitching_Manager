package com.kitching.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.data.dto.StaffLevelDTO
import com.kitching.data.firebase.COLLECTION_STAFF_LEVEL
import kotlinx.coroutines.tasks.await

class StaffLevelDataSourceImpl(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) :
    StaffLevelDataSource {
    override suspend fun getStaffLevel(staffLevelId: String): StaffLevelDTO =
        db.collection(COLLECTION_STAFF_LEVEL).document(staffLevelId).get().await()
            .toObject(StaffLevelDTO::class.java) ?: StaffLevelDTO("", "")

    override suspend fun getStaffLevels(teamId: String): List<StaffLevelDTO> =
        db.collection(COLLECTION_STAFF_LEVEL).whereEqualTo("teamId", teamId)
            .get().await()
            .toObjects(StaffLevelDTO::class.java)

    override suspend fun createStaffLevel(teamId: String, staffLevelName: String) = runCatching {
        db.collection(COLLECTION_STAFF_LEVEL).add(
            StaffLevelDTO(
                id = "",
                teamId = teamId,
                name = staffLevelName
            )
        ).await().apply {
            this.update("id", id).await()
        }
    }.isSuccess

    override suspend fun updateStaffLevel(staffLevelId: String, name: String) = runCatching {
        db.collection(COLLECTION_STAFF_LEVEL).document(staffLevelId).update("name", name)
            .await()
    }.isSuccess

    override suspend fun deleteStaffLevel(staffLevelId: String) = runCatching {
        db.collection(COLLECTION_STAFF_LEVEL).document(staffLevelId).delete().await()
    }.isSuccess
}