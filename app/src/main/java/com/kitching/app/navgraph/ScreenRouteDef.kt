package com.kitching.app.navgraph

sealed class ScreenRouteDef(val routeName: String) {
    data object PrepTab: ScreenRouteDef("Prep")
    data object RecipeTab: ScreenRouteDef("Recipe")
    data object ScheduleTab: ScreenRouteDef("Schedule")
    data object OrderTab: ScreenRouteDef("Order")
    data object OtherTab: ScreenRouteDef("Other")

    sealed interface InnerContent{
        data object RecipeDetail : ScreenRouteDef("detail")
        data object RecipeEdit : ScreenRouteDef("detail/edit")
        data object PrepDetail : ScreenRouteDef("PrepDetail")
        data object OrderDetail : ScreenRouteDef("OrderDetail")
        data object InviteCode : ScreenRouteDef("InviteCode")
        data object NoticeList : ScreenRouteDef("NoticeList")
        data object NoticeDetail : ScreenRouteDef("NoticeDetail")
        data object NoticeCreateOrUpdate : ScreenRouteDef("NoticeCreateOrUpdate")
        data object StaffLevel : ScreenRouteDef("StaffLevel")
        data object ScheduleTime : ScreenRouteDef("ScheduleTime")
        data object MemberList : ScreenRouteDef("MemberList")
        data object MemberDetail : ScreenRouteDef("MemberDetail")
    }
}