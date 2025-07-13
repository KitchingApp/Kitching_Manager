package com.kitching.app.navgraph

import android.os.Parcelable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.kitching.app.common.CommonState
import com.kitching.app.common.navIfNew
import com.kitching.app.ui.screen.order.OrderDetailScreen
import com.kitching.app.ui.screen.order.OrderMainScreen
import com.kitching.app.ui.screen.other.InviteCodeScreen
import com.kitching.app.ui.screen.other.OtherMainScreen
import com.kitching.app.ui.screen.other.memberlist.MemberDetailScreen
import com.kitching.app.ui.screen.other.memberlist.MemberListScreen
import com.kitching.app.ui.screen.other.notice.NoticeCreateOrModifyScreen
import com.kitching.app.ui.screen.other.notice.NoticeDetailScreen
import com.kitching.app.ui.screen.other.notice.NoticeListScreen
import com.kitching.app.ui.screen.other.scheduletime.ScheduleTimeCreateOrUpdateScreen
import com.kitching.app.ui.screen.other.scheduletime.ScheduleTimeScreen
import com.kitching.app.ui.screen.prep.PrepMainScreen
import com.kitching.app.ui.screen.prep.subdivisionscreen.PrepDetailScreen
import com.kitching.app.ui.screen.recipe.RecipeMainScreen
import com.kitching.app.ui.screen.recipe.innercontent.RecipeCreateScreen
import com.kitching.app.ui.screen.recipe.innercontent.RecipeCreateUseExcelScreen
import com.kitching.app.ui.screen.recipe.innercontent.RecipeDetailScreen
import com.kitching.app.ui.screen.recipe.innercontent.RecipeEditScreen
import com.kitching.app.ui.screen.recipe.innercontent.RecipeUploadInProgressScreen
import com.kitching.app.ui.screen.schedule.ScheduleMainScreen
import kotlin.reflect.typeOf


/**
 * Parcelable로 정의한 Type을 Map으로 변환하는 함수
 *
 */
inline fun <reified T : Parcelable?> typeMap(isNullableAllowed: Boolean = false) =
    mapOf(typeOf<T>() to parcelableNavType<T>(isNullableAllowed))

fun NavGraphBuilder.scheduleSliceNavGraph(
    commonState: CommonState,
) {
    navigation<Route.ScheduleGraph>(
        startDestination = Route.ScheduleGraph.getStartDestination()
    ) {
        composable<Route.ScheduleGraph.ScheduleMain> {
            ScheduleMainScreen(
                commonState = commonState
            )
        }
    }
}

fun NavGraphBuilder.prepSliceNavGraph(
    commonState: CommonState,
    navController: NavController
) {
    navigation<Route.PrepGraph>(
        startDestination = Route.PrepGraph.getStartDestination()
    ) {
        composable<Route.PrepGraph.PrepMain> {
            PrepMainScreen(
                commonState = commonState,
                navigateToPrepDetail = { categoryItem ->
                    navController.navIfNew(Route.PrepGraph.PrepDetail(categoryItem))
                }
            )
        }
        composable<Route.PrepGraph.PrepDetail>(
            typeMap = typeMap<CategoryItem>()
        ) { navBackStackEntry ->
            PrepDetailScreen(
                commonState = commonState,
                categoryItemForScreen = navBackStackEntry.toRoute<Route.PrepGraph.PrepDetail>().categoryItem,
                navigateToPrep = { navController.popBackStack() }
            )
        }
    }
}

fun NavGraphBuilder.recipeSliceNavGraph(
    commonState: CommonState,
    navController: NavController
) {
    navigation<Route.RecipeGraph>(
        startDestination = Route.RecipeGraph.getStartDestination()
    ) {
        composable<Route.RecipeGraph.RecipeMain> {
            RecipeMainScreen(
                commonState = commonState,
                navigateToCreateUesDevice = { navController.navIfNew(Route.RecipeGraph.RecipeCreate) },
                navigateToCreateWithExcelFile = { navController.navIfNew(Route.RecipeGraph.RecipeCreateUseExcel) },
                navigateToDetail = { recipe ->
                    navController.navIfNew(
                        Route.RecipeGraph.RecipeDetail(
                            RecipeDetailItem.domainToItem(
                                recipe
                            )
                        )
                    )
                }
            )
        }
        composable<Route.RecipeGraph.RecipeDetail>(
            typeMap = typeMap<RecipeDetailItem>()
        ) { navBackStackEntry ->
            val recipe = navBackStackEntry.toRoute<Route.RecipeGraph.RecipeDetail>().recipe
            RecipeDetailScreen(
                recipe = recipe,
                commonState = commonState,
                navigateToEdit = { navController.navIfNew(Route.RecipeGraph.RecipeEdit(recipe)) },
                naviagateToList = { navController.popBackStack() }
            )
        }
        composable<Route.RecipeGraph.RecipeEdit>(
            typeMap = typeMap<RecipeDetailItem>()
        ) { navBackStackEntry ->
            val recipe = navBackStackEntry.toRoute<Route.RecipeGraph.RecipeEdit>().recipe
            RecipeEditScreen(
                commonState = commonState,
                recipe = recipe,
                navigateToDetail = { navController.popBackStack() },
                navigateToRecipe = { navController.navIfNew(Route.RecipeGraph.RecipeMain) }
            )
        }
        composable<Route.RecipeGraph.RecipeCreate> {
            RecipeCreateScreen(
                commonState = commonState,
                navigateToRecipe = { navController.popBackStack() }
            )
        }
        composable<Route.RecipeGraph.RecipeCreateUseExcel> {
            RecipeCreateUseExcelScreen(
                commonState = commonState,
                navigateToRecipeUploadInProgress = { navController.navIfNew(Route.RecipeGraph.RecipeUploadInProgress) },
                navigateToRecipe = { navController.popBackStack() }
            )
        }
        composable<Route.RecipeGraph.RecipeUploadInProgress> {
            RecipeUploadInProgressScreen(
                commonState = commonState
            )
        }
    }
}

fun NavGraphBuilder.orderSliceNavGraph(
    commonState: CommonState,
    navController: NavController,
) {
    navigation<Route.OrderGraph>(
        startDestination = Route.OrderGraph.getStartDestination()
    ) {
        composable<Route.OrderGraph.OrderMain> {
            OrderMainScreen(
                commonState = commonState,
                onClickItem = { categoryItem ->
                    navController.navIfNew(Route.OrderGraph.OrderDetail(categoryItem))
                }
            )
        }
        composable<Route.OrderGraph.OrderDetail>(
            typeMap = typeMap<CategoryItem>()
        ) { navBackStackEntry ->
            OrderDetailScreen(
                commonState = commonState,
                categoryItemForScreen = navBackStackEntry.toRoute<Route.OrderGraph.OrderDetail>().categoryItem,
                navigateToOrderMain = { navController.popBackStack() }
            )
        }
    }
}

fun NavGraphBuilder.otherSliceNavGraph(
    commonState: CommonState,
    navController: NavController
) {
    navigation<Route.OtherGraph>(
        startDestination = Route.OtherGraph.getStartDestination()
    ) {
        composable<Route.OtherGraph.OtherMain> {
            OtherMainScreen(
                commonState = commonState,
                navigateToEachItem = { otherMenuItem ->
                    navController.navIfNew(otherMenuItem.destination) }
            )
        }
        composable<Route.OtherGraph.InviteCode> {
            InviteCodeScreen(
                commonState = commonState,
                navigateToOther = { navController.popBackStack() }
            )
        }
        composable<Route.OtherGraph.NoticeList> {
            NoticeListScreen(
                commonState = commonState,
                navigateToCreateNotice = {
                    navController.navIfNew(Route.OtherGraph.NoticeCreateOrUpdate(null))
                },
                navigateToNoticeDetail = { notice ->
                    navController.navIfNew(Route.OtherGraph.NoticeDetail(notice))
                },
                navigateToOther = { navController.popBackStack() }
            )
        }
        composable<Route.OtherGraph.NoticeDetail>(
            typeMap = typeMap<NoticeItem>()
        ) { navBackStackEntry ->
            NoticeDetailScreen(
                commonState = commonState,
                notice = navBackStackEntry.toRoute<Route.OtherGraph.NoticeDetail>().notice,
                navigateToNoticeList = { navController.navIfNew(Route.OtherGraph.NoticeList) },
                navigateToNoticeModify = { navController.navIfNew(Route.OtherGraph.NoticeCreateOrUpdate)}
            )
        }
        composable<Route.OtherGraph.NoticeCreateOrUpdate>(
            typeMap = typeMap<NoticeItem?>(true)
        ) { navBackStackEntry ->
            NoticeCreateOrModifyScreen(
                commonState = commonState,
                notice = navBackStackEntry.toRoute<Route.OtherGraph.NoticeCreateOrUpdate>().notice,
                navigateToNoticeList = { navController.popBackStack() },
                popBackStack = { navController.popBackStack() }
            )
        }
        composable<Route.OtherGraph.ScheduleTime> {
            ScheduleTimeScreen(
                commonState = commonState,
                naviagateToCreateOfModifyScheduleTime = { scheduleTime ->
                    navController.navIfNew(Route.OtherGraph.ScheduleTimeCreateOrUpdate(scheduleTime))
                },
                navigateToOther = { navController.popBackStack() }
            )
        }
        composable<Route.OtherGraph.ScheduleTimeCreateOrUpdate>(
            typeMap = typeMap<ScheduleTimeItem?>(true)
        ) { navBackStackEntry ->
            val scheduleTime = navBackStackEntry.toRoute<Route.OtherGraph.ScheduleTimeCreateOrUpdate>().scheduleTime
            ScheduleTimeCreateOrUpdateScreen(
                commonState = commonState,
                navigateToScheduleTimeList = { navController.navIfNew(Route.OtherGraph.ScheduleTimeCreateOrUpdate(scheduleTime)) },
                scheduleTime = scheduleTime
            )
        }
        composable<Route.OtherGraph.MemberList> {
            MemberListScreen(
                commonState = commonState,
                navigateToMemberDetail = { member ->
                    navController.navIfNew(Route.OtherGraph.MemberDetail(member))
                },
                navigateToOther = { navController.popBackStack() }
            )
        }
        composable<Route.OtherGraph.MemberDetail>(
            typeMap = typeMap<MemberItem>()
        ) { navBackStackEntry ->
            MemberDetailScreen(
                commonState = commonState,
                member = navBackStackEntry.toRoute<Route.OtherGraph.MemberDetail>().member,
                navigateToMemberList = { navController.popBackStack() }
            )
        }
    }
}