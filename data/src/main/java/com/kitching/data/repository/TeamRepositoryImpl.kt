package com.kitching.data.repository

import com.kitching.data.datasource.TeamDataSource
import com.kitching.data.datasource.TeamDataSourceImpl
import com.kitching.data.datasource.UserTeamDataSource
import com.kitching.data.datasource.UserTeamDataSourceImpl
import com.kitching.domain.AppResult
import com.kitching.domain.entities.Team
import com.kitching.domain.repository.TeamRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.util.UUID

class TeamRepositoryImpl(
    private val teamDataSource: TeamDataSource = TeamDataSourceImpl(),
    private val userTeamDataSource: UserTeamDataSource = UserTeamDataSourceImpl()
) : TeamRepository {
    override fun getTeamsByUserId(userId: String) = flow {
        emit(AppResult.Loading)
        val teams = userTeamDataSource.getUserTeams(userId)
        if (teams.isEmpty()) emit(AppResult.Success(emptyList()))
        else emit(AppResult.Success(teams.map {
            val team = teamDataSource.getTeam(it.id)
            if (team !== null) {
                Team(
                    teamId = it.id,
                    teamName = team.teamName,
                    teamAmount = team.teamAmount
                )
            } else {
                throw Exception()
            }
        }))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override fun getTeam(teamId: String) = flow {
        emit(AppResult.Loading)
        val team = teamDataSource.getTeam(teamId)
        if (team != null) {
            emit(
                AppResult.Success(
                    Team(
                        teamId = team.id,
                        teamName = team.teamName,
                        teamAmount = team.teamAmount
                    )
                )
            )
        } else throw Throwable("team is not exists")
    }.catch {
        emit(AppResult.Failure(it))
    }

    override fun createTeam(
        ownerId: String, teamName: String, teamAmount: Int
    ) = flow {
        emit(AppResult.Loading)
        val inviteCode = UUID.randomUUID().toString().replace("-", "")
        val teamId =
            emit(
                AppResult.Success(
                    userTeamDataSource.createUserTeams(
                        userId = ownerId,
                        teamId = teamDataSource.createTeam(
                            inviteCode,
                            ownerId,
                            teamName,
                            teamAmount
                        ),
                        staffLevelId = ""
                    )
                )
            )
    }.catch {
        emit(AppResult.Failure(it))
    }
}