package com.kitching.data.repository

import com.kitching.data.datasource.FcmTokenDataSource
import com.kitching.data.datasource.FcmTokenDataSourceImpl
import com.kitching.data.datasource.PushMessageDataSource
import com.kitching.data.datasource.PushMessageDataSourceImpl
import com.kitching.data.datasource.TeamDataSource
import com.kitching.data.datasource.TeamDataSourceImpl
import com.kitching.data.exception.PushMessageFailedException
import com.kitching.domain.AppResult
import com.kitching.domain.entities.FcmToken
import com.kitching.domain.entities.Schedule
import com.kitching.domain.repository.PushMessageRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class PushMessageRepositoryImpl(
    private val fcmTokenDataSource: FcmTokenDataSource = FcmTokenDataSourceImpl(),
    private val pushMessageDataSource: PushMessageDataSource = PushMessageDataSourceImpl(),
    private val teamDataSource: TeamDataSource = TeamDataSourceImpl()
) : PushMessageRepository {
    override fun sendRejectPushMessage(
        teamId: String,
        schedule: Schedule,
        rejectReason: String
    ) = flow {
        emit(AppResult.Loading)
        val failedList = mutableListOf<Pair<FcmToken, String>>()
        val successedPushDeviceList = mutableListOf<String>()
        val registrationTokens = fcmTokenDataSource.getTokens(schedule.userId)
        registrationTokens.forEach { token ->
            try {
                val res = pushMessageDataSource.sendRejectPushMessage(
                    teamName = teamDataSource.getTeam(teamId).teamName,
                    scheduleDate = schedule.date,
                    scheduleTimeName = schedule.scheduleTimeName,
                    rejectReason = rejectReason,
                    registrationToken = token.token
                )
                if (res.code() == 200) {
                    successedPushDeviceList.add(token.deviceModel)
                } else {
                    failedList.add(Pair(token.toDomain(), res.errorBody()?.string() ?: ""))
                }
            } catch (throwable: Throwable) {
                failedList.add(Pair(token.toDomain(), throwable.message ?: ""))
            }
        }
        // 여러대의 기기중 한대라도 푸시알림 성공적으로 보냈다면 성공으로 간주(기준 회의 필요)
        if (successedPushDeviceList.isEmpty()) {
            emit(AppResult.Failure(PushMessageFailedException(userId = schedule.userId, failedList = failedList)))
        } else {
            emit(AppResult.Success(true))
        }
    }.catch { emit(AppResult.Failure(it)) }
}