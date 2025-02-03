package com.kitching.app.navgraph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.kitching.app.common.CommonState
import com.kitching.app.ui.screen.order.OrderDetailScreen
import com.kitching.app.ui.screen.prep.subdivisionscreen.PrepDetailScreen
import com.kitching.app.ui.screen.recipe.innercontent.RecipeDetail

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
            RecipeDetail(
                recipeId = backStackEntry.arguments?.getString("recipeId"),
                commonState = commonState
            )
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
}