package com.kitching.app.navgraph

import android.util.Base64
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.kitching.app.common.CommonState
import com.kitching.app.ui.screen.order.OrderDetailScreen
import com.kitching.app.ui.screen.other.InviteCodeScreen
import com.kitching.app.ui.screen.other.StaffLevelScreen
import com.kitching.app.ui.screen.other.memberlist.MemberDetailScreen
import com.kitching.app.ui.screen.other.memberlist.MemberListScreen
import com.kitching.app.ui.screen.other.notice.NoticeCreateOrModifyScreen
import com.kitching.app.ui.screen.other.notice.NoticeDetailScreen
import com.kitching.app.ui.screen.other.notice.NoticeListScreen
import com.kitching.app.ui.screen.other.scheduletime.ScheduleTimeCreateOrUpdateScreen
import com.kitching.app.ui.screen.other.scheduletime.ScheduleTimeScreen
import com.kitching.app.ui.screen.prep.subdivisionscreen.PrepDetailScreen
import com.kitching.app.ui.screen.recipe.innercontent.RecipeDetailScreen
import com.kitching.app.ui.screen.recipe.innercontent.RecipeEditScreen
import com.kitching.domain.entities.Notice
import com.kitching.domain.entities.ScheduleTime
import kotlinx.serialization.json.Json

fun NavGraphBuilder.sliceNavGraph(
    commonState: CommonState
) {
    navigation(
        startDestination = ScreenRouteDef.RecipeTab.routeName,
        route = "recipe_detail"
    ) {
        composable(
            ScreenRouteDef.InnerContent.RecipeDetail.routeName + "/{recipeId}", // detail/1
            arguments = listOf(navArgument("recipeId") { NavType.StringType })
        ) { backStackEntry ->
            RecipeDetailScreen(
                recipeId = backStackEntry.arguments?.getString("recipeId").toString(),
                commonState = commonState
            )
        }

        composable(
            route = ScreenRouteDef.InnerContent.RecipeEdit.routeName + "/{recipeId}",
            arguments = listOf(navArgument("recipeId") { type = NavType.StringType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId").toString()
            RecipeEditScreen(recipeId = recipeId, commonState = commonState)
        }
    }
    navigation(
        startDestination = ScreenRouteDef.PrepTab.routeName,
        route = "prep_detail"
    ) {
        composable(
            ScreenRouteDef.InnerContent.PrepDetail.routeName + "/{categoryId}/{categoryName}/{categoryColor}",
            arguments = listOf(
                navArgument("categoryId") { NavType.StringType },
                navArgument("categoryName") { NavType.StringType },
                navArgument("categoryColor") { NavType.StringType }
            )
        ) { backStackEntry ->
            PrepDetailScreen(
                commonState = commonState,
                categoryId = backStackEntry.arguments?.getString("categoryId"),
                categoryName = backStackEntry.arguments?.getString("categoryName"),
                categoryColor = backStackEntry.arguments?.getString("categoryColor"),
            )
        }
    }
    navigation(
        startDestination = ScreenRouteDef.OrderTab.routeName,
        route = "order_detail"
    ) {
        composable(
            ScreenRouteDef.InnerContent.OrderDetail.routeName + "/{categoryId}/{categoryName}/{categoryColor}",
            arguments = listOf(
                navArgument("categoryId") { NavType.StringType },
                navArgument("categoryName") { NavType.StringType },
                navArgument("categoryColor") { NavType.StringType }
            )
        ) { backStackEntry ->
            OrderDetailScreen(
                commonState = commonState,
                categoryId = backStackEntry.arguments?.getString("categoryId"),
                categoryName = backStackEntry.arguments?.getString("categoryName"),
                categoryColor = backStackEntry.arguments?.getString("categoryColor"),
            )
        }
    }
    navigation(
        startDestination = ScreenRouteDef.OtherTab.routeName,
        route = "other_menus"
    ) {
        composable(
            ScreenRouteDef.InnerContent.InviteCode.routeName
        ) {
            InviteCodeScreen(commonState = commonState)
        }

        composable(
            ScreenRouteDef.InnerContent.NoticeList.routeName
        ) {
            NoticeListScreen(commonState = commonState)
        }

        composable(
            route = ScreenRouteDef.InnerContent.NoticeDetail.routeName + "/{notice}",
            arguments = listOf(navArgument("notice") { type = NavType.StringType})
        ) { backStackEntry ->
            NoticeDetailScreen(notice = Json.decodeFromString<Notice>(backStackEntry.arguments?.getString("notice") ?: ""), commonState = commonState)
        }

        composable(
            route = ScreenRouteDef.InnerContent.NoticeCreateOrUpdate.routeName + "/{notice}" +"/{writerName}",
            arguments = listOf(
                navArgument("notice") { type = NavType.StringType; nullable },
                navArgument("writerName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val json = backStackEntry.arguments?.getString("notice")
            val notice = if (!json.isNullOrEmpty()) {
                Json.decodeFromString<Notice>(json) // 빈 문자열이 아니면 변환
            } else {
                null // 빈 문자열이면 null 처리
            }
            NoticeCreateOrModifyScreen(
                commonState = commonState,
                notice = notice,
                writerName = backStackEntry.arguments?.getString("writerName") ?: ""
            )
        }

        composable(
            ScreenRouteDef.InnerContent.StaffLevel.routeName
        ) {
            StaffLevelScreen(commonState = commonState)
        }

        composable(
            ScreenRouteDef.InnerContent.ScheduleTime.routeName
        ) {
            ScheduleTimeScreen(commonState = commonState)
        }

        composable(
            route = ScreenRouteDef.InnerContent.ScheduleTimeCreateOrUpdate.routeName + "/{scheduleTime}",
            arguments = listOf(
                navArgument("scheduleTime") { type = NavType.StringType; nullable }
            )
        ) { backStackEntry ->
            val json = backStackEntry.arguments?.getString("scheduleTime")
            val scheduleTime = if (!json.isNullOrEmpty()) {
                Json.decodeFromString<ScheduleTime>(json)
            } else {
                null
            }
            ScheduleTimeCreateOrUpdateScreen(
                commonState = commonState,
                scheduleTime = scheduleTime,
            )
        }

        composable(
            ScreenRouteDef.InnerContent.MemberList.routeName
        ) {
            MemberListScreen(commonState = commonState)
        }

        composable(
            route = ScreenRouteDef.InnerContent.MemberDetail.routeName + "/{member}",
            arguments = listOf(navArgument("member") { type = NavType.StringType})
        ) { backStackEntry ->
            val encodedMember = backStackEntry.arguments?.getString("member") ?: ""
            val decodedJson = String(Base64.decode(encodedMember, Base64.URL_SAFE or Base64.NO_WRAP))
            MemberDetailScreen(
                commonState = commonState,
                member = Json.decodeFromString(decodedJson)
            )
        }
    }
}