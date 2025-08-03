package com.kitching.data

import com.kitching.data.dto.NoticeMessageReq
import com.kitching.data.dto.NoticeMessageRes
import com.kitching.data.dto.ScheduleRejectPushMsgReq
import com.kitching.data.dto.ScheduleRejectPushMsgRes
import com.kitching.data.moshi.moshi
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

const val FIREBASE_FUNCTION_BASE_URL = "https://us-central1-kitching-91adf.cloudfunctions.net/"

interface FirebaseFunctionApiService {
    @POST("/pushMessage")
    suspend fun sendPushMessage(@Body scheduleRejectPushMsgReq: ScheduleRejectPushMsgReq): Response<ScheduleRejectPushMsgRes>

    @POST("/sendNoticeMessage")
    suspend fun sendNoticeMessage(@Body noticeMessageReq: NoticeMessageReq): Response<NoticeMessageRes>

    companion object {
        private var firebaseFunctionApiService: FirebaseFunctionApiService? = null
        fun getInstance(): FirebaseFunctionApiService {
            if(firebaseFunctionApiService == null) {
                firebaseFunctionApiService = Retrofit.Builder()
                    .baseUrl(FIREBASE_FUNCTION_BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build()
                    .create(FirebaseFunctionApiService::class.java)
            }
            return firebaseFunctionApiService!!
        }
    }
}