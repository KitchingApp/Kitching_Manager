package com.kitching.app.ui.screen.recipe.innercontent

import android.util.Log
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.kitching.app.R
import com.kitching.app.common.ActionIconInfo
import com.kitching.app.common.CommonState
import com.kitching.app.common.NavigationIconInfo
import com.kitching.app.navgraph.RecipeDetailItem
import com.kitching.app.ui.factory.viewModelFactory
import com.kitching.app.ui.model.RecipeViewModel
import com.kitching.app.ui.screen.commondialog.BasicConfirmDialog
import com.kitching.app.ui.screen.commondialog.DropdownOptionMenu
import com.kitching.app.ui.theme.H2
import com.kitching.app.ui.theme.H4_m
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.domain.AppResult

@Composable
fun RecipeDetailScreen(
    recipe: RecipeDetailItem,
    navigateToEdit: () -> Unit,
    naviagateToList: () -> Unit,
    commonState: CommonState,
    viewModel: RecipeViewModel = viewModel(factory = viewModelFactory)
) {
    val deleteState by viewModel.deleteResult.collectAsStateWithLifecycle()
    var showOptionMenu by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        title = "Kitching",
        containerColor = NeutralGray0,
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = { naviagateToList() },
        actionIconInfo = ActionIconInfo.OPTION,
        onClickActionIcon = { showOptionMenu = true }
    )

    LaunchedEffect(deleteState) {
        Log.d("deleteState", deleteState.toString())
        if (deleteState is AppResult.Success) {
            showDeleteDialog = false
            naviagateToList()
        }
    }

    KitchingManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            if (showOptionMenu) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopEnd
                ) {
                    DropdownOptionMenu(
                        onDismissRequest = { showOptionMenu = false },
                        onClickModify = {
                            showOptionMenu = false
                            navigateToEdit()
                        },
                        onClickDelete = {
                            showOptionMenu = false
                            showDeleteDialog = true
                        }
                    )
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 27.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    AsyncImage(
                        model = recipe.picture.replace("recipeImage/", "recipeImage%2F"),
                        contentDescription = "${recipe.recipeName} 이미지",
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    Text(
                        text = recipe.recipeName,
                        style = H2,
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 10.dp)
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
                        IngredientsTable(ingredients = recipe.ingredient)
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
                        StepTable(steps = recipe.steps)
                    }
                }
            }
            if (showDeleteDialog) {
                BasicConfirmDialog(
                    message = stringResource(R.string.recipe_delete_dialog_message),
                    confirmText = stringResource(R.string.button_delete),
                    onClickConfirm = {
                        viewModel.deleteRecipe(recipe.recipeId)
                    },
                    cancelText = stringResource(R.string.button_cancel),
                    onClickCancel = { showDeleteDialog = false }
                )
            }
        }
    }
}