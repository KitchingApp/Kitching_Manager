package com.kitching.data.repository

import com.kitching.data.datasource.StaffLevelDataSource
import com.kitching.data.datasource.StaffLevelDataSourceImpl
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
    private val userTeamDataSource: UserTeamDataSource = UserTeamDataSourceImpl(),
    private val userDataSource: UserDataSource = UserDataSourceImpl(),
    private val staffLevelDataSource: StaffLevelDataSource = StaffLevelDataSourceImpl()
): UserTeamRepository {
    override fun getAllMembers(teamId: String) = flow {
        emit(AppResult.Loading)
        emit(AppResult.Success(
            userTeamDataSource.getAllMembers(teamId).map {
                val user = userDataSource.getUser(it.userId) ?: throw Throwable("User not Exist")
                Member(
                    userTeamId = it.id,
                    userId = it.userId,
                    userName = user.userName,
                    userImage = user.userImage,
                    staffLevelId = it.staffLevelId ?: "",
                    staffLevelName = if(it.staffLevelId == "") "" else staffLevelDataSource.getStaffLevel(it.staffLevelId).name,
                    manager = it.manager
                )
            }
        ))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override fun updateMember(
        userTeamId: String,
        staffLevelId: String,
        manager: Boolean
    ) = flow {
        emit(AppResult.Loading)
        emit(AppResult.Success(userTeamDataSource.updateMember(
            userTeamId = userTeamId,
            staffLevelId = staffLevelId,
            manager = manager
        )))
    }.catch {
        emit(AppResult.Failure(it))
    }
}