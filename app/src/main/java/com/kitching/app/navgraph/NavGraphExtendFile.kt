package com.kitching.app.navgraph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.kitching.app.common.CommonState
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
}