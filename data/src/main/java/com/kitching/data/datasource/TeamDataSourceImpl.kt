package com.kitching.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.data.dto.TeamDTO
import com.kitching.data.exception.FailedCRUDInFirebaseException
import com.kitching.data.exception.TeamNotFoundException
import com.kitching.data.firebase.COLLECTION_TEAM
import kotlinx.coroutines.tasks.await

class TeamDataSourceImpl(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) :
    TeamDataSource {
    override suspend fun getTeam(teamId: String) = runCatching {
        db.collection(COLLECTION_TEAM).document(teamId).get().await()
            .toObject(TeamDTO::class.java) ?: throw TeamNotFoundException(teamId)
    }.getOrElse {
        throw if(it is TeamNotFoundException) it.getException()
        else FailedCRUDInFirebaseException(it).getException()
    }

    override suspend fun createTeam(
        ownerId: String,
        inviteCode: String,
        teamName: String,
        teamAmount: Int,
    ) = runCatching {
        db.collection(COLLECTION_TEAM).add(
            TeamDTO(
                id = "",
                inviteCode = inviteCode,
                ownerId = ownerId,
                teamName = teamName,
                teamAmount = teamAmount
            )
        ).await().apply {
            update("id", id).await()
        }.id
    }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }

    override suspend fun getTeamList(teamId: String): List<TeamDTO> = runCatching {
        db.collection(COLLECTION_TEAM).whereNotEqualTo("id", teamId).get().await()
            .toObjects(TeamDTO::class.java)
    }.getOrElse { throw FailedCRUDInFirebaseException(it).getException() }
}