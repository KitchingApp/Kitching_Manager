package com.kitching.data.repository

import com.kitching.data.datasource.LoginDataSource
import com.kitching.data.datasource.LoginDataSourceImpl
import com.kitching.domain.AppResult
import com.kitching.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class LoginRepositoryImpl(
    private val dataSource: LoginDataSource = LoginDataSourceImpl(),
): LoginRepository {
    override fun checkAndSaveUser(
        uid: String,
        userName: String,
        userImage: String,
    ): Flow<AppResult<Unit>> = flow {
        emit(AppResult.Loading)
        emit(AppResult.Success(dataSource.checkAndSaveUser(uid, userName, userImage)))
    }.catch {
        emit(AppResult.Failure(it))
    }
}