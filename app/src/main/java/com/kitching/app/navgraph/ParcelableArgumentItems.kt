package com.kitching.app.navgraph

import android.os.Parcelable
import com.kitching.app.util.customFormat
import com.kitching.domain.entities.Member
import com.kitching.domain.entities.ScheduleTime
import kotlinx.parcelize.Parcelize
import java.time.LocalTime

@Parcelize
data class CategoryItem(
    val categoryId: String,
    val categoryName: String,
    val categoryColor: String
): Parcelable

@Parcelize
data class MemberItem(
    val userTeamId: String,
    val userId: String,
    val userName: String,
    val userImage: String,
    val staffLevelId: String,
    val staffLevelName: String,
    val manager: Boolean
): Parcelable {
    companion object {
        fun domainToItem(domain: Member) = MemberItem(
            userTeamId = domain.userTeamId,
            userId = domain.userId,
            userName = domain.userName,
            userImage = domain.userImage,
            staffLevelId = domain.staffLevelId,
            staffLevelName = domain.staffLevelName,
            manager = domain.manager
        )
    }
}

@Parcelize
data class NoticeItem(
    val noticeId: String,
    val writerName: String,
    val date: String,
    val title: String,
    val content: String,
): Parcelable

@Parcelize
data class ScheduleTimeItem(
    val scheduleTimeId: String,
    val scheduleTimeName: String,
    val startTime: String,
    val endTime: String,
): Parcelable {
    companion object {
        fun init() = ScheduleTimeItem(
            scheduleTimeId = "",
            scheduleTimeName = "",
            startTime = LocalTime.now().customFormat(),
            endTime = LocalTime.now().customFormat()
        )

        fun domainToItem(scheduleTime: ScheduleTime) = ScheduleTimeItem(
            scheduleTimeId = scheduleTime.scheduleTimeId,
            scheduleTimeName = scheduleTime.scheduleTimeName,
            startTime = scheduleTime.startTime,
            endTime = scheduleTime.endTime
        )
    }
}