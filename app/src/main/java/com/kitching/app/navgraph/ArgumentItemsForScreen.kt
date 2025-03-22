package com.kitching.app.navgraph

import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class CategoryItemForScreen(
    val categoryId: String,
    val categoryName: String,
    val categoryColor: String
)

@Serializable
data class MemberItemForScreen(
    val userTeamId: String,
    val userId: String,
    val userName: String,
    val userImage: String,
    val staffLevelId: String,
    val staffLevelName: String,
    val manager: Boolean
)

@Serializable
data class NoticeItemForScreen(
    val noticeId: String,
    val writerName: String,
    val date: String,
    val title: String,
    val content: String,
)

@Serializable
data class ScheduleTimeItemForScreen(
    val scheduleTimeId: String,
    val scheduleTimeName: String,
    val startTime: String,
    val endTime: String,
)

inline fun <reified T : Any> argumentItemsForScreenType (
    isNullableAllowed: Boolean = false
) = object : NavType<T>(isNullableAllowed = isNullableAllowed) {
    override fun get(bundle: Bundle, key: String) = bundle.getString(key)?.let { Json.decodeFromString<T>(it) }

    override fun parseValue(value: String): T = Json.decodeFromString<T>(value)

    override fun serializeAsValue(value: T): String = Json.encodeToString<T>(value)

    override fun put(bundle: Bundle, key: String, value: T) = bundle.putString(key, Json.encodeToString<T>(value))
}