package com.kitching.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.data.dto.ScheduleTimeDTO
import com.kitching.data.exception.FailedCRUDInFirebaseException
import com.kitching.data.exception.ScheduleTimeNotFoundException
import com.kitching.data.firebase.COLLECTION_SCHEDULE_TIME
import kotlinx.coroutines.tasks.await

class ScheduleTimeDataSourceImpl(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) :
    ScheduleTimeDataSource {
    override suspend fun getScheduleTime(scheduleTimeId: String)
    = runCatching {
        db.collection(COLLECTION_SCHEDULE_TIME).document(scheduleTimeId).get().await()
            .toObject(ScheduleTimeDTO::class.java) ?: throw ScheduleTimeNotFoundException(
            scheduleTimeId
        )
    }.getOrElse {
        throw if(it is ScheduleTimeNotFoundException) it.getException()
        else FailedCRUDInFirebaseException(it).getException()
    }

    override suspend fun getScheduleTimes(teamId: String): List<ScheduleTimeDTO> =
        runCatching {
            db.collection(COLLECTION_SCHEDULE_TIME).whereEqualTo("teamId", teamId).get().await()
                .toObjects(ScheduleTimeDTO::class.java)
        }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }

    override suspend fun createScheduleTime(
        teamId: String,
        name: String,
        startTime: String,
        endTime: String,
    ) = runCatching {
        val docRef = db.collection(COLLECTION_SCHEDULE_TIME).add(
            ScheduleTimeDTO(
                id = "",
                teamId = teamId,
                name = name,
                startTime = startTime,
                endTime = endTime,
            )
        ).await()

        docRef.update("id", docRef.id).await()

        Unit
    }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }

    override suspend fun updateScheduleTime(
        scheduleTimeId: String,
        name: String,
        startTime: String,
        endTime: String
    ) = runCatching {
        db.collection(COLLECTION_SCHEDULE_TIME).document(scheduleTimeId)
            .update("name", name, "startTime", startTime, "endTime", endTime)
            .await()

        Unit
    }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }

    override suspend fun deleteScheduleTime(scheduleTimeId: String) = runCatching {
        db.collection(COLLECTION_SCHEDULE_TIME).document(scheduleTimeId).delete().await()

        Unit
    }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }
}