package com.kitching.data.repository

import com.kitching.data.datasource.PrepDataSourceImpl
import com.kitching.domain.AppResult
import com.kitching.domain.entities.Prep
import com.kitching.domain.repository.PrepRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class PrepRepositoryImpl(
    private val prepDataSource: PrepDataSourceImpl = PrepDataSourceImpl()
) : PrepRepository {
    override fun getPrepList(categoryId: String): Flow<AppResult<List<Prep>>> =
        flow {
            emit(AppResult.Loading)
            val prepList = prepDataSource.getPrepList(categoryId)
            if (prepList.isEmpty()) emit(AppResult.Success(emptyList()))
            else emit(AppResult.Success(prepList.map { it.toDomain() }))
        }.catch {
            emit(AppResult.Failure(it))
        }

    override fun createPrep(categoryId: String, name: String) =
        flow {
            emit(AppResult.Loading)
            val result = prepDataSource.createPrepList(categoryId, name)
            emit(AppResult.Success(result))
        }.catch {
            emit(AppResult.Failure(it))
        }

    override fun updatePrep(prepId: String, name: String) = flow {
        emit(AppResult.Loading)
        val result = prepDataSource.updatePrepList(prepId, name)
        emit(AppResult.Success(result))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override fun deletePrep(prepId: String) = flow {
        emit(AppResult.Loading)
        val result = prepDataSource.deletePrepList(prepId)
        emit(AppResult.Success(result))
    }.catch {
        emit(AppResult.Failure(it))
    }
}