package com.kitching.data.repository

import com.kitching.data.datasource.UserDataSource
import com.kitching.data.datasource.UserDataSourceImpl
import com.kitching.domain.AppResult
import com.kitching.domain.entities.Member
import com.kitching.domain.entities.User
import com.kitching.domain.repository.UserRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(
    private val userDataSource: UserDataSource = UserDataSourceImpl()
): UserRepository {
    override fun getUser(userId: String) = flow {
        emit(AppResult.Loading)
        emit(AppResult.Success(
            userDataSource.getUser(userId).toDomain()
        ))
    }.catch {
        emit(AppResult.Failure(it))
    }
}