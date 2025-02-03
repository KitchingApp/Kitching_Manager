package com.kitching.app.navgraph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.kitching.app.common.CommonState
import com.kitching.app.ui.screen.recipe.innercontent.RecipeDetailScreen
import com.kitching.app.ui.screen.recipe.innercontent.RecipeEditScreen

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
}