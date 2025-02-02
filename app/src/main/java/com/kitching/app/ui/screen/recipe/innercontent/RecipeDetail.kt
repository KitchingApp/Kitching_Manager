package com.kitching.app.ui.screen.recipe.innercontent

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kitching.app.common.ActionIconInfo
import com.kitching.app.common.CommonState
import com.kitching.app.common.NavigationIconInfo
import com.kitching.app.ui.screen.recipe.dummyRecipes
import com.kitching.app.ui.theme.H2
import com.kitching.app.ui.theme.H4_m
import com.kitching.app.ui.theme.KitchingManagerTheme

@Composable
fun RecipeDetail(
    recipeId: String,
    commonState: CommonState
) {
    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = {
            commonState.navController.popBackStack()
        },
        actionIconInfo = ActionIconInfo.CHECK,
        onClickActionIcon = {
        }
    )

    val recipeDetail = dummyRecipes.find { it.id == recipeId }

    if (recipeDetail == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "레시피 정보를 찾을 수 없습니다.",
                style = H2,
            )
        }
        return
    }

    KitchingManagerTheme {
        Surface(
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
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }

                item {
                    Text(
                        text = recipeDetail.name,
                        style = H2,
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 10.dp)
                    )
                }

                item {
                    Text(text = "재료", style = H4_m, modifier = Modifier.padding(start = 20.dp, end = 20.dp))
                }

                item {
                    Box(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
                        IngredientsTable(ingredients = recipeDetail.ingredients)
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
    }
}