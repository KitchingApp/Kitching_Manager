package com.kitching.data.repository

import com.kitching.data.datasource.PrepCategoryDataSource
import com.kitching.data.datasource.PrepCategoryDataSourceImpl
import com.kitching.domain.AppResult
import com.kitching.domain.repository.PrepCategoryRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class PrepCategoryRepositoryImpl(
    private val prepCategoryDataSource: PrepCategoryDataSource = PrepCategoryDataSourceImpl(),
) : PrepCategoryRepository {
    override suspend fun getPrepCategory(teamId: String)
    = flow {
            emit(AppResult.Loading)
            val prepCategories = prepCategoryDataSource.getPrepCategory(teamId)
            if (prepCategories.isEmpty()) emit(AppResult.Success(emptyList()))
            else emit(AppResult.Success(prepCategories.map { it.toDomain() }))
        }.catch {
            emit(AppResult.Failure(it))
        }

    override suspend fun createPrepCategory(
        teamId: String,
        categoryName: String,
        color: String
    ) = flow {
        emit(AppResult.Loading)
        emit(
            AppResult.Success(
                prepCategoryDataSource.createPrepCategory(
                    teamId,
                    categoryName,
                    color
                )
            )
        )
    }.catch {
        emit(AppResult.Failure(it))
    }

    override suspend fun updatePrepCategory(
        categoryId: String,
        categoryName: String,
        color: String
    ) = flow {
            emit(AppResult.Loading)
            val result = prepCategoryDataSource.updatePrepCategory(categoryId, categoryName, color)
            emit(AppResult.Success(result))
        }.catch {
            emit(AppResult.Failure(it))
        }

    override suspend fun deletePrepCategory(scheduleId: String)
    = flow {
            emit(AppResult.Loading)
            val result = prepCategoryDataSource.deletePrepCategory(scheduleId)
            emit(AppResult.Success(result))
        }.catch {
            emit(AppResult.Failure(it))
        }
}