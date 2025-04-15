package com.kitching.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.data.dto.ScheduleDTO
import com.kitching.data.exception.FailedCRUDInFirebaseException
import com.kitching.data.exception.ScheduleNotFoundException
import com.kitching.data.firebase.COLLECTION_SCHEDULE
import kotlinx.coroutines.tasks.await

class ScheduleDataSourceImpl(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) :
    ScheduleDataSource {
    override suspend fun getSchedules(teamId: String, dateString: String): List<ScheduleDTO> =
        runCatching {
            db.collection(COLLECTION_SCHEDULE)
                .whereEqualTo("teamId", teamId)
                .whereEqualTo("date", dateString)
                .get()
                .await()
                .toObjects(ScheduleDTO::class.java)
        }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }

    override suspend fun getSchedule(scheduleId: String): ScheduleDTO  = runCatching {
        db.collection(
            COLLECTION_SCHEDULE
        ).document(scheduleId).get().await().toObject(ScheduleDTO::class.java)
            ?: throw ScheduleNotFoundException(scheduleId).getException()
    }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }

    override suspend fun createSchedule(
        teamId: String,
        dateString: String,
        userId: String,
        scheduleTimeId: String,
        fix: Boolean
    ) = runCatching {
        val docRef = db.collection(COLLECTION_SCHEDULE).add(
            ScheduleDTO(
                id = "",
                date = dateString,
                scheduleTimeId = scheduleTimeId,
                teamId = teamId,
                userId = userId,
                fix = fix,
            )
        ).await()

        docRef.update("id", docRef.id).await()

        Unit
    }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }

    override suspend fun deleteSchedule(scheduleId: String) = runCatching {
        db.collection(COLLECTION_SCHEDULE).document(scheduleId).delete()

        Unit
    }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }

    override suspend fun applySchedule(scheduleId: String) = runCatching {
        db.collection(COLLECTION_SCHEDULE).document(scheduleId).update("fix", true).await()

        Unit
    }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }
}