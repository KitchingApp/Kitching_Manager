package com.kitching.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.data.dto.UserTeamDTO
import com.kitching.data.exception.FailedCRUDInFirebaseException
import com.kitching.data.exception.MemberNotFoundException
import com.kitching.data.firebase.COLLECTION_USER_TEAM
import kotlinx.coroutines.tasks.await

class UserTeamDataSourceImpl(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) :
    UserTeamDataSource {
    override suspend fun getAllMembers(teamId: String): List<UserTeamDTO> = runCatching {
        db.collection(COLLECTION_USER_TEAM).whereEqualTo("teamId", teamId).get().await()
            .toObjects(UserTeamDTO::class.java)
    }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }

    override suspend fun getMember(teamId: String, userId: String) =
        db.collection(COLLECTION_USER_TEAM).whereEqualTo("teamId", teamId)
            .whereEqualTo("userId", userId).get().await().documents.first()
            .toObject(UserTeamDTO::class.java) ?: throw MemberNotFoundException(
            teamId = teamId,
            userId = userId
        ).getException()

    override suspend fun getUserTeams(userId: String): List<UserTeamDTO> = runCatching {
        db.collection(COLLECTION_USER_TEAM).whereEqualTo("userId", userId).get().await()
            .toObjects(UserTeamDTO::class.java)
    }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }

    override suspend fun createUserTeams(
        userId: String,
        teamId: String,
        staffLevelId: String,
        manager: Boolean
    ) = runCatching {
        val docRef = db.collection(COLLECTION_USER_TEAM).add(
            UserTeamDTO(
                id = "",
                userId = userId,
                teamId = teamId,
                manager = manager,
                staffLevelId = staffLevelId
            )
        ).await()

        docRef.update("id", docRef.id)

        Unit
    }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }

    override suspend fun updateMember(
        userTeamId: String,
        staffLevelId: String,
        manager: Boolean
    ) = runCatching {
        db.collection(COLLECTION_USER_TEAM).document(userTeamId)
            .update("staffLevelId", staffLevelId, "manager", manager)

        Unit
    }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }
}