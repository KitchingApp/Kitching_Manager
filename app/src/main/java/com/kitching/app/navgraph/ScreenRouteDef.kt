package com.kitching.app.navgraph

import kotlinx.serialization.Serializable

@Serializable
open class BottomNavigationItem

@Serializable
object ScheduleTab : BottomNavigationItem()

@Serializable
object PrepGraph: BottomNavigationItem()
@Serializable
data object PrepTab
@Serializable
data class PrepDetail(val categoryItemForScreen: CategoryItemForScreen)

@Serializable
object RecipeGraph: BottomNavigationItem()
@Serializable
data object RecipeTab
@Serializable
data object RecipeCreate
@Serializable
data object RecipeCreateUseExcel
@Serializable
data object RecipeDetail
@Serializable
data object RecipeEdit

@Serializable
object OrderGraph: BottomNavigationItem()
@Serializable
data object OrderTab
@Serializable
data class OrderDetail(val categoryItemForScreen: CategoryItemForScreen)

@Serializable
object OtherGraph: BottomNavigationItem()
@Serializable
data object OtherTab
@Serializable
data object InviteCode
@Serializable
data object NoticeList
@Serializable
data class NoticeDetail(val notice: NoticeItemForScreen)
@Serializable
data class NoticeCreateOrUpdate(val notice: NoticeItemForScreen?)
@Serializable
data object StaffLevel
@Serializable
data object ScheduleTime
@Serializable
data class ScheduleTimeCreateOrUpdate(val scheduleTime: ScheduleTimeItemForScreen)
@Serializable
data object MemberList
@Serializable
data class MemberDetail(val member: MemberItemForScreen)

@Serializable
object CreateTeamScreen