package com.kitching.app.navgraph

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object CreateTeam: Route

    @Serializable
    sealed interface BottomTab {
        fun getStartDestination(): Route
    }

    @Serializable
    sealed interface OtherMenuItem

    @Serializable
    data object ScheduleGraph: BottomTab {
        override fun getStartDestination(): Route = ScheduleMain

        @Serializable
        data object ScheduleMain: Route
    }

    @Serializable
    data object PrepGraph: BottomTab {
        override fun getStartDestination(): Route = PrepMain

        @Serializable
        data object PrepMain: Route
        @Serializable
        data class PrepDetail(val categoryItem: CategoryItem)
    }

    @Parcelize
    @Serializable
    data object RecipeGraph: BottomTab, Parcelable {
        override fun getStartDestination(): Route = RecipeMain

        @Serializable
        data object RecipeMain: Route
        @Serializable
        data class RecipeCreate(val imageUri: String? = null): Route
        @Serializable
        data object RecipeCamera: Route
        @Serializable
        data object RecipeCreateUseExcel: Route
        @Serializable
        data object RecipeUploadInProgress: Route
        @Serializable
        data class RecipeDetail(val recipe: RecipeDetailItem): Route
        @Serializable
        data class RecipeEdit(val recipe: RecipeDetailItem): Route
    }

    @Serializable
    data object OrderGraph: BottomTab {
        override fun getStartDestination(): Route = OrderMain

        @Serializable
        data object OrderMain: Route
        @Serializable
        data class OrderDetail(val categoryItem: CategoryItem): Route
    }

    @Serializable
    data object OtherGraph: BottomTab {
        override fun getStartDestination(): Route = OtherMain

        @Serializable
        data object OtherMain: Route
        @Serializable
        data object InviteCode: OtherMenuItem
        @Serializable
        data object NoticeList: OtherMenuItem
        @Serializable
        data class NoticeDetail(val notice: NoticeItem): Route
        @Serializable
        data class NoticeCreateOrUpdate(val notice: NoticeItem?): Route
        @Serializable
        data object StaffLevel: OtherMenuItem
        @Serializable
        data object ScheduleTime: OtherMenuItem
        @Serializable
        data class ScheduleTimeCreateOrUpdate(val scheduleTime: ScheduleTimeItem?): Route
        @Serializable
        data object MemberList: OtherMenuItem
        @Serializable
        data class MemberDetail(val member: MemberItem): Route
    }
}