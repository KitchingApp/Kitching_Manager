package com.kitching.app.ui.screen.recipe.innercontent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.kitching.app.common.ActionIconInfo
import com.kitching.app.common.AppResultHandler
import com.kitching.app.common.CommonState
import com.kitching.app.common.NavigationIconInfo
import com.kitching.app.common.showToast
import com.kitching.app.ui.factory.viewModelFactory
import com.kitching.app.ui.model.RecipeViewModel
import com.kitching.app.ui.theme.H4_m
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.domain.AppResult
import com.kitching.domain.entities.Ingredient

@Composable
fun RecipeEditScreen(
    recipeId: String,
    commonState: CommonState,
    viewModel: RecipeViewModel = viewModel(factory = viewModelFactory)
) {
    var recipeName by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf(listOf<Ingredient>()) }
    var steps by remember { mutableStateOf(listOf<String>()) }
    var pictureUrl by remember { mutableStateOf("") }

    LaunchedEffect(recipeId) {
        viewModel.getRecipeById(recipeId)
    }

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = { commonState.navController.popBackStack() },
        actionIconInfo = ActionIconInfo.CHECK,
        onClickActionIcon = {
            viewModel.updateRecipe(
                recipeId = recipeId,
                name = recipeName,
                steps = steps,
                ingredients = ingredients
            )
        }
    )

    val recipeDetailState by viewModel.recipeDetail.collectAsState()
    val updateState by viewModel.updateResult.collectAsState()

    LaunchedEffect(recipeDetailState) {
        if (recipeDetailState is AppResult.Success) {
            val recipe = (recipeDetailState as AppResult.Success).data
            recipeName = recipe.recipeName
            ingredients = recipe.ingredient
            steps = recipe.steps
            pictureUrl = recipe.picture
        }
    }

    KitchingManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            AppResultHandler(
                state = recipeDetailState,
                onSuccess = {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 27.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        item {
                            AsyncImage(
                                model = pictureUrl,
                                contentDescription = "$recipeName 이미지",
                                modifier = Modifier.fillMaxWidth(),
                                contentScale = ContentScale.Fit
                            )
                        }

                        item {
                            OutlinedTextField(
                                value = recipeName,
                                onValueChange = { recipeName = it },
                                label = { "레시피 이름" },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp)
                            )
                        }

                        item {
                            Text(
                                text = "재료",
                                style = H4_m,
                                modifier = Modifier.padding(start = 20.dp, end = 20.dp)
                            )
                        }

                        item {
                            Box(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
                                EditIngredientsTable(ingredients) { updatedIngredients ->
                                    ingredients = updatedIngredients
                                }
                            }
                        }

                        item {
                            Text(
                                text = "레시피 순서",
                                style = H4_m,
                                modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp)
                            )
                        }

                        item {
                            Box(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
                                EditStepTable(steps) { updatedSteps ->
                                    steps = updatedSteps
                                }
                            }
                        }
                    }
                }
            )

            AppResultHandler(
                state = updateState,
                onSuccess = {
                    showToast("레시피가 수정되었습니다!")
                    commonState.navController.popBackStack()
                }
            )
        }
    }
}
