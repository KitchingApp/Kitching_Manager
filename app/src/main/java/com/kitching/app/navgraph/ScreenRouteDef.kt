package com.kitching.app.navgraph

sealed class ScreenRouteDef(val routeName: String) {
    data object PrepTab: ScreenRouteDef("Prep")
    interface PrepTabSlice {
        data object PrepDetail: ScreenRouteDef("PrepDetail")
    }

    data object RecipeTab: ScreenRouteDef("Recipe")
    interface RecipeTabSlice {
        data object RecipeCreate : ScreenRouteDef("create")
        data object RecipeCreateUseExcel : ScreenRouteDef("UploadExcel")
        data object RecipeDetail : ScreenRouteDef("detail")
        data object RecipeEdit : ScreenRouteDef("detail/edit")
    }

    data object ScheduleTab: ScreenRouteDef("Schedule")

    data object OrderTab: ScreenRouteDef("Order")
    interface OrderTabSlice {
        data object OrderDetail : ScreenRouteDef("OrderDetail")
    }

    data object OtherTab: ScreenRouteDef("Other")
    interface OtherTabSlice {
        data object InviteCode : ScreenRouteDef("InviteCode")
        data object NoticeList : ScreenRouteDef("NoticeList")
        data object NoticeDetail : ScreenRouteDef("NoticeDetail")
        data object NoticeCreateOrUpdate : ScreenRouteDef("NoticeCreateOrUpdate")
        data object StaffLevel : ScreenRouteDef("StaffLevel")
        data object ScheduleTime : ScreenRouteDef("ScheduleTime")
        data object ScheduleTimeCreateOrUpdate : ScreenRouteDef("ScheduleTimeCreateOrUpdate")
        data object MemberList : ScreenRouteDef("MemberList")
        data object MemberDetail : ScreenRouteDef("MemberDetail")
    }

    data object CreateTeamScreen: ScreenRouteDef("CreateTeam")
}