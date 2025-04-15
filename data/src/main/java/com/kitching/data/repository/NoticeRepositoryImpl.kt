package com.kitching.data.repository

import com.kitching.data.datasource.NoticeDataSource
import com.kitching.data.datasource.NoticeDataSourceImpl
import com.kitching.data.datasource.UserDataSource
import com.kitching.data.datasource.UserDataSourceImpl
import com.kitching.domain.AppResult
import com.kitching.domain.entities.Notice
import com.kitching.domain.repository.NoticeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class NoticeRepositoryImpl(
    private val userDataSource: UserDataSource = UserDataSourceImpl(),
    private val noticeDataSource: NoticeDataSource = NoticeDataSourceImpl()
) : NoticeRepository {
    override fun getNotices(teamId: String): Flow<AppResult<List<Notice>>> = flow {
        emit(AppResult.Loading)
        val notices = noticeDataSource.getNotices(teamId)
        if (notices.isEmpty()) emit(AppResult.Success(emptyList()))
        else emit(AppResult.Success(
            notices.map {
                Notice(
                    noticeId = it.id,
                    title = it.title,
                    content = it.content,
                    date = it.date,
                    writerName = userDataSource.getUser(it.writerId).userName
                )
            }
        ))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override fun createNotice(
        userId: String,
        teamId: String,
        title: String,
        content: String
    ) = flow {
        emit(AppResult.Loading)
        emit(AppResult.Success(noticeDataSource.createNotice(userId, teamId, title, content)))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override fun updateNotice(
        noticeId: String,
        title: String,
        content: String
    ) = flow {
        emit(AppResult.Loading)
        emit(AppResult.Success(noticeDataSource.updateNotice(noticeId, title, content)))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override fun deleteNotice(noticeId: String) = flow {
        emit(AppResult.Loading)
        emit(AppResult.Success(noticeDataSource.deleteNotice(noticeId)))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override fun getUserName(userId: String) = flow {
        emit(AppResult.Loading)
        emit(AppResult.Success(userDataSource.getUser(userId).userName))
    }.catch {
        emit(AppResult.Failure(it))
    }
}