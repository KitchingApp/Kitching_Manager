package com.kitching.app.ui.screen.recipe.innercontent

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kitching.app.common.ActionIconInfo
import com.kitching.app.common.CommonState
import com.kitching.app.common.NavigationIconInfo
import com.kitching.app.ui.screen.recipe.dummyRecipes
import com.kitching.app.ui.theme.H4_m
import com.kitching.app.ui.theme.KitchingManagerTheme

@Composable
fun RecipeEditScreen(
    recipeId: String,
    commonState: CommonState
) {
    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = { commonState.navController.popBackStack() },
        actionIconInfo = ActionIconInfo.CHECK,
        onClickActionIcon = {
            // TODO: 저장 로직 추가
            commonState.navController.popBackStack()
        }
    )

    val recipeDetail = dummyRecipes.find { it.id == recipeId } ?: return

    var recipeName by remember { mutableStateOf(recipeDetail.name) }
    var ingredients by remember { mutableStateOf(recipeDetail.ingredients) }
    var steps by remember { mutableStateOf(recipeDetail.steps) }

    KitchingManagerTheme {
        Surface (
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 27.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    Image(
                        painter = painterResource(id = recipeDetail.picture),
                        contentDescription = "${recipeDetail.name} 이미지",
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
                    Text(text = "재료", style = H4_m, modifier = Modifier.padding(start = 20.dp, end = 20.dp))
                }

                item {
                    Box(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
                        EditIngredientsTable(ingredients) { updatedIngredients ->
                            ingredients = updatedIngredients
                        }
                    }
                }

                item {
                    Text(text = "레시피 순서", style = H4_m, modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp))
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
    }
}