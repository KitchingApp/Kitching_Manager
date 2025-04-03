package com.kitching.app.navgraph

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object CreateTeam: Route

    @Serializable
    sealed interface BottomTab
    @Serializable
    sealed interface OtherMenuItem

    @Serializable
    data object ScheduleGraph: BottomTab {
        @Serializable
        data object ScheduleMain: Route
    }

    @Serializable
    data object PrepGraph: BottomTab {
        @Serializable
        data object PrepMain: Route
        @Serializable
        data class PrepDetail(val categoryItem: CategoryItem)
    }

    @Serializable
    data object RecipeGraph: BottomTab {
        @Serializable
        data object RecipeMain: Route
        @Serializable
        data object RecipeCreate: Route
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
        @Serializable
        data object OrderMain: Route
        @Serializable
        data class OrderDetail(val categoryItem: CategoryItem): Route
    }

    @Serializable
    data object OtherGraph: BottomTab {
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

//sealed class ScreenRouteDef(val route: String) {
//
//    data object ScheduleGraph: ScreenRouteDef("schedule_graph") {
//        data object ScheduleMain: ScreenRouteDef("schedule_main")
//    }
//
//    data object PrepGraph: ScreenRouteDef("prep_graph") {
//        data object PrepMain: ScreenRouteDef("prep_main")
//        data object PrepDetail: ScreenRouteDef("prep_detail")
//    }
//
//    data object RecipeGraph: ScreenRouteDef("recipe_graph") {
//        data object RecipeMain: ScreenRouteDef("recipe_main")
//        data object RecipeCreate: ScreenRouteDef("recipe_create")
//        data object RecipeCreateUseExcel: ScreenRouteDef("recipe_create_use_excel")
//        data object RecipeUploadInProgress: ScreenRouteDef("recipe_upload_in_progress")
//        data object RecipeDetail: ScreenRouteDef("recipe_detail")
//        data object RecipeEdit: ScreenRouteDef("recipe_edit")
//    }
//
//    data object OrderGraph: ScreenRouteDef("order_graph") {
//        data object OrderMain: ScreenRouteDef("order_main")
//        data object OrderDetail: ScreenRouteDef("order_detail")
//    }
//
//    data object OtherGraph: ScreenRouteDef("other_graph") {
//        data object OtherMain: ScreenRouteDef("other_main")
//        data object InviteCode: ScreenRouteDef("invite_code")
//        data object NoticeList: ScreenRouteDef("notice_list")
//        data object NoticeDetail: ScreenRouteDef("notice_detail")
//        data object NoticeCreateOrUpdate: ScreenRouteDef("notice_create_or_update")
//        data object StaffLevel: ScreenRouteDef("staff_level")
//        data object ScheduleTime: ScreenRouteDef("schedule_time")
//        data object ScheduleTimeCreateOrUpdate: ScreenRouteDef("schedule_time_create_or_update")
//        data object MemberList: ScreenRouteDef("member_list")
//        data object MemberDetail: ScreenRouteDef("member_detail")
//    }
//
//    data object CreateTeam: ScreenRouteDef("create_team")
//}