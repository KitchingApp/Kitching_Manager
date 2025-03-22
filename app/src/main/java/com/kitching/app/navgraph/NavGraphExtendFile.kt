package com.kitching.app.navgraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.kitching.app.common.CommonState
import com.kitching.app.ui.screen.order.OrderDetailScreen
import com.kitching.app.ui.screen.order.OrderTabScreen
import com.kitching.app.ui.screen.other.InviteCodeScreen
import com.kitching.app.ui.screen.other.OtherTabScreen
import com.kitching.app.ui.screen.other.memberlist.MemberDetailScreen
import com.kitching.app.ui.screen.other.memberlist.MemberListScreen
import com.kitching.app.ui.screen.other.notice.NoticeCreateOrModifyScreen
import com.kitching.app.ui.screen.other.notice.NoticeDetailScreen
import com.kitching.app.ui.screen.other.notice.NoticeListScreen
import com.kitching.app.ui.screen.other.scheduletime.ScheduleTimeCreateOrUpdateScreen
import com.kitching.app.ui.screen.other.scheduletime.ScheduleTimeScreen
import com.kitching.app.ui.screen.prep.PrepTabScreen
import com.kitching.app.ui.screen.prep.subdivisionscreen.PrepDetailScreen
import com.kitching.app.ui.screen.recipe.RecipeTabScreen
import com.kitching.app.ui.screen.recipe.innercontent.RecipeCreateScreen
import com.kitching.app.ui.screen.recipe.innercontent.RecipeCreateUseExcelScreen
import com.kitching.app.ui.screen.recipe.innercontent.RecipeDetailScreen
import com.kitching.app.ui.screen.recipe.innercontent.RecipeEditScreen
import kotlin.reflect.typeOf

fun NavGraphBuilder.prepSliceNavGraph(
    commonState: CommonState,
    navController: NavController
) {

    navigation<PrepGraph>(
        startDestination = PrepTab,
    ) {
        composable<PrepTab>(
        ) {
            PrepTabScreen(
                commonState = commonState,
                onClickItem = { categoryItemForScreen ->
                    navController.navigate(
                        PrepDetail(categoryItemForScreen)
                    )
                }
            )
        }
        composable<PrepDetail>(
            typeMap = mapOf(typeOf<CategoryItemForScreen>() to argumentItemsForScreenType<CategoryItemForScreen>())
        ) { backStackEntry ->
            PrepDetailScreen(
                commonState = commonState,
                categoryItemForScreen = backStackEntry.toRoute<PrepDetail>().categoryItemForScreen
            )
        }
    }
}

fun NavGraphBuilder.recipeSliceNavGraph(
    commonState: CommonState,
    navController: NavController
) {
    navigation<RecipeGraph>(
        startDestination = RecipeTab
    ) {
        composable<RecipeTab> {
            RecipeTabScreen(
                commonState = commonState,
                goToCreateWithExcelFile = {
                    navController.navigate(RecipeCreateUseExcel)
                }
            )
        }
        composable<RecipeDetail> { backStackEntry ->
            RecipeDetailScreen(
                commonState = commonState,
                recipeId = backStackEntry.toRoute()
            )
        }
        composable<RecipeEdit> { backStackEntry ->
            RecipeEditScreen(
                commonState = commonState,
                recipeId = backStackEntry.toRoute()
            )
        }
        composable<RecipeCreate> {
            RecipeCreateScreen(
                commonState = commonState
            )
        }
        composable<RecipeCreateUseExcel> {
            RecipeCreateUseExcelScreen(
                commonState = commonState,
                goToRecipeList = { navController.navigate(RecipeGraph) }
            )
        }
    }
}

fun NavGraphBuilder.orderSliceNavGraph(
    commonState: CommonState,
    navController: NavController
) {
    navigation<OrderGraph>(
        startDestination = OrderTab
    ) {
        composable<OrderTab> {
            OrderTabScreen(
                commonState = commonState,
                onClickItem = { categoryItemForScreen ->
                    navController.navigate(OrderDetail(categoryItemForScreen))
                }
            )
        }
        composable<OrderDetail>(
            typeMap = mapOf(typeOf<CategoryItemForScreen>() to argumentItemsForScreenType<CategoryItemForScreen>())
        ) { backStackEntry ->
            OrderDetailScreen(
                commonState = commonState,
                categoryItemForScreen = backStackEntry.toRoute()
            )
        }
    }
}

fun NavGraphBuilder.otherSliceNavGraph(
    commonState: CommonState,
    navController: NavController
) {
    navigation<OtherGraph>(
        startDestination = OtherTab
    ) {
        composable<OtherTab> {
            OtherTabScreen(
                commonState = commonState
            )
        }
        composable<InviteCode> {
            InviteCodeScreen(
                commonState = commonState
            )
        }
        composable<NoticeList> {
            NoticeListScreen(
                commonState = commonState,
                goToCreateNotice = {
                    navController.navigate(
                        NoticeCreateOrUpdate(
                            null
                        )
                    )
                },
                goToNoticeDetail = { notice ->
                    navController.navigate(
                        NoticeDetail(
                            notice
                        )
                    )
                }
            )
        }
        composable<NoticeDetail>(
            typeMap = mapOf(typeOf<NoticeItemForScreen>() to argumentItemsForScreenType<NoticeItemForScreen>())
        ) { backStackEntry ->
            NoticeDetailScreen(
                notice = backStackEntry.toRoute(),
                commonState = commonState,
                goToNoticeList = { navController.navigate(NoticeList) }
            )
        }
        composable<NoticeCreateOrUpdate>(
            typeMap = mapOf(typeOf<NoticeItemForScreen?>() to argumentItemsForScreenType<NoticeItemForScreen>(true))
        ) { backStackEntry ->
            NoticeCreateOrModifyScreen(
                commonState = commonState,
                onSuccessCreateOrModify = {
                    navController.navigate(NoticeList)
                },
                notice = backStackEntry.toRoute()
            )
        }
        composable<ScheduleTime> {
            ScheduleTimeScreen(
                commonState = commonState,
                goToCreateOfModifyScheduleTime = { scheduleTime ->
                    navController.navigate(
                        ScheduleTimeCreateOrUpdate(
                            ScheduleTimeItemForScreen(
                                scheduleTimeId = scheduleTime.scheduleTimeId,
                                scheduleTimeName = scheduleTime.scheduleTimeName,
                                startTime = scheduleTime.startTime,
                                endTime = scheduleTime.endTime
                            )
                        )
                    )
                }
            )
        }
        composable<ScheduleTimeCreateOrUpdate>(
            typeMap = mapOf(typeOf<ScheduleTimeItemForScreen>() to argumentItemsForScreenType<ScheduleTimeItemForScreen>())
        ) { backStackEntry ->
            ScheduleTimeCreateOrUpdateScreen(
                commonState = commonState,
                goToScheduleTimeList = { navController.navigate(ScheduleTime) },
                scheduleTime = backStackEntry.toRoute()
            )
        }
        composable<MemberList> {
            MemberListScreen(
                commonState = commonState,
                onMemberClick = { member ->
                    navController.navigate(
                        MemberDetail(
                            MemberItemForScreen(
                                userTeamId = member.userTeamId,
                                userId = member.userId,
                                userName = member.userName,
                                userImage = member.userImage,
                                staffLevelId = member.staffLevelId,
                                staffLevelName = member.staffLevelName,
                                manager = member.manager
                            )
                        )
                    )
                }
            )
        }
        composable<MemberDetail>(
            typeMap = mapOf(typeOf<MemberItemForScreen>() to argumentItemsForScreenType<MemberItemForScreen>())
        ) { backStackEntry ->
            MemberDetailScreen(
                commonState = commonState,
                member = backStackEntry.toRoute()
            )
        }
    }
}