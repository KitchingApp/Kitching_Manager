package com.kitching.app.navgraph

sealed class ScreenRouteDef(val route: String) {

    data object ScheduleGraph: ScreenRouteDef("schedule_graph") {
        data object ScheduleMain: ScreenRouteDef("schedule_main")
    }

    data object PrepGraph: ScreenRouteDef("prep_graph") {
        data object PrepMain: ScreenRouteDef("prep_main")
        data object PrepDetail: ScreenRouteDef("prep_detail")
    }

    data object RecipeGraph: ScreenRouteDef("recipe_graph") {
        data object RecipeMain: ScreenRouteDef("recipe_main")
        data object RecipeCreate: ScreenRouteDef("recipe_create")
        data object RecipeCreateUseExcel: ScreenRouteDef("recipe_create_use_excel")
        data object RecipeDetail: ScreenRouteDef("recipe_detail")
        data object RecipeEdit: ScreenRouteDef("recipe_edit")
    }

    data object OrderGraph: ScreenRouteDef("order_graph") {
        data object OrderMain: ScreenRouteDef("order_main")
        data object OrderDetail: ScreenRouteDef("order_detail")
    }

    data object OtherGraph: ScreenRouteDef("other_graph") {
        data object OtherMain: ScreenRouteDef("other_main")
        data object InviteCode: ScreenRouteDef("invite_code")
        data object NoticeList: ScreenRouteDef("notice_list")
        data object NoticeDetail: ScreenRouteDef("notice_detail")
        data object NoticeCreateOrUpdate: ScreenRouteDef("notice_create_or_update")
        data object StaffLevel: ScreenRouteDef("staff_level")
        data object ScheduleTime: ScreenRouteDef("schedule_time")
        data object ScheduleTimeCreateOrUpdate: ScreenRouteDef("schedule_time_create_or_update")
        data object MemberList: ScreenRouteDef("member_list")
        data object MemberDetail: ScreenRouteDef("member_detail")
    }

    data object CreateTeam: ScreenRouteDef("create_team")
}