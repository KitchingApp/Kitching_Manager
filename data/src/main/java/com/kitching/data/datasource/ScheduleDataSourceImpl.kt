package com.kitching.data.datasource

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.data.dto.ScheduleDTO
import com.kitching.data.firebase.COLLECTION_SCHEDULE
import kotlinx.coroutines.tasks.await

class ScheduleDataSourceImpl(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) :
    ScheduleDataSource {
    override suspend fun getSchedules(teamId: String, dateString: String): List<ScheduleDTO> =
        db.collection(COLLECTION_SCHEDULE)
            .whereEqualTo("teamId", teamId)
            .whereEqualTo("date", dateString)
            .get()
            .await()
            .toObjects(ScheduleDTO::class.java)

    override suspend fun createSchedule(
        teamId: String,
        dateString: String,
        userId: String,
        scheduleTimeId: String,
        fix: Boolean
    ) = runCatching {
        db.collection(COLLECTION_SCHEDULE).add(
            ScheduleDTO(
                id = "",
                date = dateString,
                scheduleTimeId = scheduleTimeId,
                teamId = teamId,
                userId = userId,
                fix = fix,
            )
        ).await().apply {
            this.update("id", this.id).await()
        }
    }.isSuccess

    override suspend fun deleteSchedule(scheduleId: String) = runCatching {
        db.collection(COLLECTION_SCHEDULE).document(scheduleId).delete()
    }.isSuccess

    override suspend fun applySchedule(scheduleId: String) = runCatching {
        db.collection(COLLECTION_SCHEDULE).document(scheduleId).update("fix", true).await()
    }.isSuccess
}