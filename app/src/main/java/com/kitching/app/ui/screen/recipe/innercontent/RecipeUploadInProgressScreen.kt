package com.kitching.app.ui.screen.recipe.innercontent

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.kitching.app.R
import com.kitching.app.common.ActionIconInfo
import com.kitching.app.common.CommonState
import com.kitching.app.common.NavigationIconInfo
import com.kitching.app.ui.screen.common.ProgressIndicatorScreenWithNoDialog
import com.kitching.app.ui.theme.KitchingManagerTheme

@Composable
fun RecipeUploadInProgressScreen(
    commonState: CommonState,
) {
    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        navIconInfo = NavigationIconInfo.NULL,
        onClickNavIcon = {},
        actionIconInfo = ActionIconInfo.NULL,
        onClickActionIcon = {}
    )

    KitchingManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            ProgressIndicatorScreenWithNoDialog(R.string.recipe_upload_in_progress_screen_message)
        }
    }
}