package com.kitching.app.ui.screen.recipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kitching.app.common.ActionIconInfo
import com.kitching.app.common.AppResultHandler
import com.kitching.app.common.CommonState
import com.kitching.app.common.KitchingApplication
import com.kitching.app.common.NavigationIconInfo
import com.kitching.app.ui.factory.viewModelFactory
import com.kitching.app.ui.item.RecipeItem
import com.kitching.app.ui.model.RecipeViewModel
import com.kitching.app.ui.screen.commondialog.RecipeCreateOptionMenu
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.util.PreferencesDataStore
import kotlinx.coroutines.launch

@Composable
fun RecipeMainScreen(
    commonState: CommonState,
    navigateToCreateUesDevice: () -> Unit,
    navigateToCreateWithExcelFile: () -> Unit,
    viewModel: RecipeViewModel = viewModel(factory = viewModelFactory)
) {
    var showCreateOptionMenu by remember { mutableStateOf(false) }

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        title = "Kitching",
        containerColor = NeutralGray0,
        navIconInfo = NavigationIconInfo.DRAWER,
        onClickNavIcon = {
            if (commonState.topAppBarState.value.drawerState.isOpen) {
                commonState.coroutineScope.launch { commonState.topAppBarState.value.drawerState.close() }
            } else {
                commonState.coroutineScope.launch { commonState.topAppBarState.value.drawerState.open() }
            }
        },
        actionIconInfo = ActionIconInfo.ADD,
        onClickActionIcon = {
            showCreateOptionMenu = true
        }
    )

    LaunchedEffect(Unit) {
        val teamId = PreferencesDataStore(KitchingApplication.getInstance()).getTeamId()
        viewModel.getRecipesByTeamId(teamId)
    }

    val recipeListState by viewModel.recipeList.collectAsStateWithLifecycle()

    KitchingManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            if(showCreateOptionMenu) {
                Box(
                    contentAlignment = Alignment.TopEnd
                ) {
                    RecipeCreateOptionMenu(
                        onDismissRequest = { showCreateOptionMenu = false },
                        onClickUseDevice = {
                            showCreateOptionMenu = false
                            navigateToCreateUesDevice()
                        },
                        onClickUseExcelFile = {
                            showCreateOptionMenu = false
                            navigateToCreateWithExcelFile()
                        }
                    )
                }
            }
            AppResultHandler(
                state = recipeListState,
                onSuccess = { recipes ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(20.dp),
                            verticalArrangement = Arrangement.spacedBy(20.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(recipes) { recipe ->
                                RecipeItem(recipe = recipe, commonState = commonState)
                            }
                        }
                    }
                }
            )
        }
    }
}