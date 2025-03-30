package com.kitching.app.navgraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kitching.app.common.CommonState
import com.kitching.app.common.getArgsFromSavedStateHandle
import com.kitching.app.common.navigateWithArgs
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
import com.kitching.app.ui.screen.schedule.ScheduleMainScreen

fun NavGraphBuilder.scheduleSliceNavGraph(
    commonState: CommonState,
    navController: NavController
) {
    navigation(
        route = ScreenRouteDef.ScheduleGraph.route,
        startDestination = ScreenRouteDef.ScheduleGraph.ScheduleMain.route
    ) {
        composable(
            route = ScreenRouteDef.ScheduleGraph.ScheduleMain.route
        ) {
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

    navigation(
        route = ScreenRouteDef.PrepGraph.route,
        startDestination = ScreenRouteDef.PrepGraph.PrepMain.route,
    ) {
        composable(
            route = ScreenRouteDef.PrepGraph.PrepMain.route
        ) {
            PrepMainScreen(
                commonState = commonState,
                navigateToPrepDetail = { categoryItem ->
                    navController.navigateWithArgs(
                        route = ScreenRouteDef.PrepGraph.PrepDetail.route,
                        args = categoryItem
                    )
                }
            )
        }
        composable(
            route = ScreenRouteDef.PrepGraph.PrepDetail.route,
        ) {
            navController.getArgsFromSavedStateHandle<CategoryItem>()?.let {
                PrepDetailScreen(
                    commonState = commonState,
                    categoryItemForScreen = it
                )
            }
        }
    }
}

fun NavGraphBuilder.recipeSliceNavGraph(
    commonState: CommonState,
    navController: NavController
) {
    navigation(
        route = ScreenRouteDef.RecipeGraph.route,
        startDestination = ScreenRouteDef.RecipeGraph.RecipeMain.route
    ) {
        composable(
            route = ScreenRouteDef.RecipeGraph.RecipeMain.route
        ) {
            RecipeMainScreen(
                commonState = commonState,
                navigateToCreateUesDevice = { navController.navigate(ScreenRouteDef.RecipeGraph.RecipeCreate.route) },
                navigateToCreateWithExcelFile = { navController.navigate(ScreenRouteDef.RecipeGraph.RecipeCreateUseExcel.route) },
                navigateToDetail = { recipe ->
                    navController.navigateWithArgs(
                        route = ScreenRouteDef.RecipeGraph.RecipeDetail.route,
                        args = RecipeDetailItem.domainToItem(recipe)
                    )
                }
            )
        }
        composable(
            route = ScreenRouteDef.RecipeGraph.RecipeDetail.route
        ) {
            navController.getArgsFromSavedStateHandle<RecipeDetailItem>()?.let {
                RecipeDetailScreen(
                    recipe = it,
                    commonState = commonState,
                    navigateToEdit = { navController.navigateWithArgs(
                        route = ScreenRouteDef.RecipeGraph.RecipeEdit.route,
                        args = it
                    ) },
                    naviagateToList = { navController.popBackStack() }
                )
            }
        }
        composable(
            route = ScreenRouteDef.RecipeGraph.RecipeEdit.route
        ) {
            navController.getArgsFromSavedStateHandle<RecipeDetailItem>()?.let {
                RecipeEditScreen(
                    commonState = commonState,
                    recipe = it,
                    navigateToDetail = { navController.popBackStack() }
                )
            }

        }
        composable(
            route = ScreenRouteDef.RecipeGraph.RecipeCreate.route
        ) {
            RecipeCreateScreen(
                commonState = commonState
            )
        }
        composable(
            route = ScreenRouteDef.RecipeGraph.RecipeCreateUseExcel.route
        ) {
            RecipeCreateUseExcelScreen(
                commonState = commonState,
                navigateToRecipeUploadInProgress = { navController.navigate(ScreenRouteDef.RecipeGraph.RecipeUploadInProgress.route) }
            )
        }
    }
}

fun NavGraphBuilder.orderSliceNavGraph(
    commonState: CommonState,
    navController: NavController,
) {
    navigation(
        route = ScreenRouteDef.OrderGraph.route,
        startDestination = ScreenRouteDef.OrderGraph.OrderMain.route
    ) {
        composable(
            route = ScreenRouteDef.OrderGraph.OrderMain.route
        ) {
            OrderMainScreen(
                commonState = commonState,
                onClickItem = { categoryItem ->
                    navController.navigateWithArgs(
                        route = ScreenRouteDef.OrderGraph.OrderDetail.route,
                        args = categoryItem
                    )
                }
            )
        }
        composable(
            route = ScreenRouteDef.OrderGraph.OrderDetail.route
        ) {
            navController.getArgsFromSavedStateHandle<CategoryItem>()?.let {
                OrderDetailScreen(
                    commonState = commonState,
                    categoryItemForScreen = it
                )
            }
        }
    }
}

fun NavGraphBuilder.otherSliceNavGraph(
    commonState: CommonState,
    navController: NavController
) {
    navigation(
        route = ScreenRouteDef.OtherGraph.route,
        startDestination = ScreenRouteDef.OtherGraph.OtherMain.route
    ) {
        composable(
            route = ScreenRouteDef.OtherGraph.OtherMain.route
        ) {
            OtherMainScreen(
                commonState = commonState
            )
        }
        composable(
            route = ScreenRouteDef.OtherGraph.InviteCode.route
        ) {
            InviteCodeScreen(
                commonState = commonState
            )
        }
        composable(
            route = ScreenRouteDef.OtherGraph.NoticeList.route
        ) {
            NoticeListScreen(
                commonState = commonState,
                navigateToCreateNotice = {
                    navController.navigate(
                        ScreenRouteDef.OtherGraph.NoticeCreateOrUpdate.route
                    )
                },
                navigateToNoticeDetail = { notice ->
                    navController.navigateWithArgs(
                        route = ScreenRouteDef.OtherGraph.NoticeDetail.route,
                        args = notice
                    )
                }
            )
        }
        composable(
            route = ScreenRouteDef.OtherGraph.NoticeDetail.route
        ) {
            navController.getArgsFromSavedStateHandle<NoticeItem>()?.let {
                NoticeDetailScreen(
                    commonState = commonState,
                    notice = it,
                    navigateToNoticeList = { navController.navigate(ScreenRouteDef.OtherGraph.NoticeList.route) }
                )
            }
        }
        composable(
            route = ScreenRouteDef.OtherGraph.NoticeCreateOrUpdate.route
        ) {
            NoticeCreateOrModifyScreen(
                commonState = commonState,
                notice = navController.getArgsFromSavedStateHandle(),
                navigateToNoticeList = { navController.navigate(ScreenRouteDef.OtherGraph.NoticeList.route) }
            )
        }
        composable(
            route = ScreenRouteDef.OtherGraph.ScheduleTime.route
        ) {
            ScheduleTimeScreen(
                commonState = commonState,
                naviagateToCreateOfModifyScheduleTime = { scheduleTime ->
                    navController.navigateWithArgs(
                        route = ScreenRouteDef.OtherGraph.ScheduleTimeCreateOrUpdate.route,
                        args = scheduleTime
                    )
                }
            )
        }
        composable(
            route = ScreenRouteDef.OtherGraph.ScheduleTimeCreateOrUpdate.route
        ) {
            ScheduleTimeCreateOrUpdateScreen(
                commonState = commonState,
                navigateToScheduleTimeList = { navController.navigate(ScreenRouteDef.OtherGraph.ScheduleTime.route) },
                scheduleTime = navController.getArgsFromSavedStateHandle()
            )
        }
        composable(
            route = ScreenRouteDef.OtherGraph.MemberList.route
        ) {
            MemberListScreen(
                commonState = commonState,
                navigateToMemberDetail = { member ->
                    navController.navigateWithArgs(
                        route = ScreenRouteDef.OtherGraph.MemberDetail.route,
                        args = member
                    )
                }
            )
        }
        composable(
            route = ScreenRouteDef.OtherGraph.MemberDetail.route
        ) {
            navController.getArgsFromSavedStateHandle<MemberItem>()?.let {
                MemberDetailScreen(
                    commonState = commonState,
                    member = it
                )
            }
        }
    }
}