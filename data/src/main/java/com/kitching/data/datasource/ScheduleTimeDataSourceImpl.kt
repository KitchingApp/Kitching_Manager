package com.kitching.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.data.dto.ScheduleTimeDTO
import com.kitching.data.firebase.COLLECTION_SCHEDULE_TIME
import kotlinx.coroutines.tasks.await

class ScheduleTimeDataSourceImpl(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) :
    ScheduleTimeDataSource {
    override suspend fun getScheduleTime(scheduleTimeId: String)
    = db.collection(COLLECTION_SCHEDULE_TIME).document(scheduleTimeId).get().await()
            .toObject(ScheduleTimeDTO::class.java)

    override suspend fun getScheduleTimes(teamId: String): List<ScheduleTimeDTO> =
        db.collection(COLLECTION_SCHEDULE_TIME).whereEqualTo("teamId", teamId).get().await()
            .toObjects(ScheduleTimeDTO::class.java)

    override suspend fun createScheduleTime(
        teamId: String,
        name: String,
        startTime: String,
        endTime: String,
    ) = runCatching {
        db.collection(COLLECTION_SCHEDULE_TIME).add(
            ScheduleTimeDTO(
                id = "",
                teamId = teamId,
                name = name,
                startTime = startTime,
                endTime = endTime,
            )
        ).await().apply {
            update("id", id).await()
        }
    }.isSuccess

    override suspend fun updateScheduleTime(
        scheduleTimeId: String,
        name: String,
        startTime: String,
        endTime: String
    ) = runCatching {
        db.collection(COLLECTION_SCHEDULE_TIME).document(scheduleTimeId)
            .update("name", name, "startTime", startTime, "endTime", endTime)
            .await()
    }.isSuccess

    override suspend fun deleteScheduleTime(scheduleTimeId: String) = runCatching {
        db.collection(COLLECTION_SCHEDULE_TIME).document(scheduleTimeId).delete().await()
    }.isSuccess
}