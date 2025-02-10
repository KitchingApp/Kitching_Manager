package com.kitching.data.repository

import com.kitching.data.datasource.StaffLevelDataSource
import com.kitching.data.datasource.StaffLevelDataSourceImpl
import com.kitching.domain.AppResult
import com.kitching.domain.entities.StaffLevel
import com.kitching.domain.repository.StaffLevelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class StaffLevelRepositoryImpl(
    private val staffLevelDataSource: StaffLevelDataSource = StaffLevelDataSourceImpl()
) : StaffLevelRepository {
    override fun getStaffLevels(teamId: String): Flow<AppResult<List<StaffLevel>>> =
        flow {
            emit(AppResult.Loading)
            val staffLevels = staffLevelDataSource.getStaffLevels(teamId)
            if (staffLevels.isEmpty()) emit(AppResult.Success(emptyList()))
            else emit(AppResult.Success(staffLevels.map {
                StaffLevel(
                    staffLevelId = it.id ?: "",
                    staffLevelName = it.name ?: ""
                )
            }))
        }.catch {
            emit(AppResult.Failure(it))
        }

    override fun createStaffLevel(
        teamId: String,
        name: String
    ) = flow {
        emit(AppResult.Loading)
        emit(AppResult.Success(staffLevelDataSource.createStaffLevel(teamId, name)))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override fun updateStaffLevel(
        staffLevelId: String,
        name: String
    ) = flow {
        emit(AppResult.Loading)
        emit(AppResult.Success(staffLevelDataSource.updateStaffLevel(staffLevelId, name)))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override fun deleteStaffLevel(staffLevelId: String) = flow {
        emit(AppResult.Loading)
        emit(AppResult.Success(staffLevelDataSource.deleteStaffLevel(staffLevelId)))
    }.catch {
        emit(AppResult.Failure(it))
    }
}