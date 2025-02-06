package com.kitching.data.repository

import com.kitching.data.datasource.StaffLevelDataSource
import com.kitching.data.datasource.StaffLevelDataSourceImpl
import com.kitching.data.datasource.TeamDataSource
import com.kitching.data.datasource.TeamDataSourceImpl
import com.kitching.data.datasource.UserDataSource
import com.kitching.data.datasource.UserDataSourceImpl
import com.kitching.data.datasource.UserTeamDataSource
import com.kitching.data.datasource.UserTeamDataSourceImpl
import com.kitching.domain.AppResult
import com.kitching.domain.entities.Member
import com.kitching.domain.repository.UserTeamRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class UserTeamRepositoryImpl(
    private val teamDataSource: TeamDataSource = TeamDataSourceImpl(),
    private val userTeamDataSource: UserTeamDataSource = UserTeamDataSourceImpl(),
    private val userDataSource: UserDataSource = UserDataSourceImpl(),
    private val staffLevelDataSource: StaffLevelDataSource = StaffLevelDataSourceImpl()
): UserTeamRepository {
    override fun getAllMembers(teamId: String) = flow {
        emit(AppResult.Loading)
        emit(AppResult.Success(
            userTeamDataSource.getAllMembers(teamId).map {
                Member(
                    userId = it.userId,
                    userName = userDataSource.getUser(it.userId)?.userName ?: throw Throwable("User not Exist"),
                    staffLevelId = it.staffLevelId,
                    staffLevelName = staffLevelDataSource.getStaffLevel(it.staffLevelId)?.name,
                    manager = it.manager
                )
            }
        ))
    }.catch {
        emit(AppResult.Failure(it))
    }
}