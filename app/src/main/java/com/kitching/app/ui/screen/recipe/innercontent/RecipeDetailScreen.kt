package com.kitching.app.ui.screen.recipe.innercontent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.kitching.app.common.ActionIconInfo
import com.kitching.app.common.AppResultHandler
import com.kitching.app.common.CommonState
import com.kitching.app.common.NavigationIconInfo
import com.kitching.app.ui.factory.viewModelFactory
import com.kitching.app.ui.model.RecipeViewModel
import com.kitching.app.ui.theme.H2
import com.kitching.app.ui.theme.H4_m
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.PrimaryGreen300

@Composable
fun RecipeDetailScreen(
    recipeId: String,
    commonState: CommonState,
    viewModel: RecipeViewModel = viewModel(factory = viewModelFactory),
) {
    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        title = "Kitching",
        containerColor = NeutralGray0,
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = {
            commonState.navController.popBackStack()
        },
        actionIconInfo = ActionIconInfo.EDIT,
        onClickActionIcon = {
            commonState.navController.navigate("detail/edit/${recipeId}")
        }
    )

    LaunchedEffect(Unit) {
        viewModel.getRecipeById(recipeId)
    }

    val recipeDetailState by viewModel.recipeDetail.collectAsStateWithLifecycle()

    KitchingManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            AppResultHandler(
                state = recipeDetailState,
                onSuccess = { recipeDetail ->
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 27.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        item {
                            AsyncImage(
                                model = recipeDetail.picture,
                                contentDescription = "${recipeDetail.recipeName} 이미지",
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        item {
                            Text(
                                text = recipeDetail.recipeName,
                                style = H2,
                                modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 10.dp)
                            )
                        }

                        item {
                            Text(text = "재료", style = H4_m, modifier = Modifier.padding(start = 20.dp, end = 20.dp))
                        }

                        item {
                            Box(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
                                IngredientsTable(ingredients = recipeDetail.ingredient)
                            }
                        }

                        item {
                            Text(text = "레시피 순서", style = H4_m, modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp))
                        }

                        item {
                            Box(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
                                StepTable(steps = recipeDetail.steps)
                            }
                        }
                    }
                }
            )
        }
    }
}