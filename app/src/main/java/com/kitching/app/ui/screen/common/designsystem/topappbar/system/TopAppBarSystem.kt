package com.kitching.app.ui.screen.common.designsystem.topappbar.system

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.kitching.app.R
import com.kitching.app.ui.compositionlocal.LocalMainState
import com.kitching.app.ui.theme.H3
import com.kitching.app.ui.theme.H3_m
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.NeutralGray800
import com.kitching.app.ui.theme.PrimaryGreen300

sealed class TopAppBarNavigationIcon(
    val icon: Int,
    val description: Int?,
    open val onClick: () -> Unit
) {
    data class Drawer(override val onClick: () -> Unit) : TopAppBarNavigationIcon(
        R.drawable.icon_hamburger_menu,
        R.string.navigation_icon_drawer_description,
        onClick
    )

    data class Back(override val onClick: () -> Unit) : TopAppBarNavigationIcon(
        R.drawable.icon_arrow_back,
        R.string.navigation_icon_back_description,
        onClick
    )

    data object Null : TopAppBarNavigationIcon(-1, null, {})
}

sealed class TopAppBarActionIcon(
    val icon: Int,
    val description: Int?,
    open val onClick: () -> Unit
) {
    data class Add(override val onClick: () -> Unit) :
        TopAppBarActionIcon(R.drawable.icon_add, R.string.action_icon_add_description, onClick)

    data class Option(override val onClick: () -> Unit) :
        TopAppBarActionIcon(
            R.drawable.icon_options,
            R.string.action_icon_option_description,
            onClick
        )

    data class Check(override val onClick: () -> Unit) :
        TopAppBarActionIcon(R.drawable.icon_check, R.string.action_icon_check_description, onClick)

    data class Edit(override val onClick: () -> Unit) :
        TopAppBarActionIcon(R.drawable.icon_edit, R.string.action_icon_edit_description, onClick)

    data object Null : TopAppBarActionIcon(-1, null, {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarBase(
    modifier: Modifier,
    title: String,
    titleStyle: TextStyle,
    containerColor: Color,
    titleContentColor: Color,
    buttonContentColor: Color,
    navigationIcon: TopAppBarNavigationIcon,
    actionIcon: TopAppBarActionIcon,
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                style = titleStyle,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor,
            titleContentColor = titleContentColor,
            actionIconContentColor = buttonContentColor,
            navigationIconContentColor = buttonContentColor
        ),
        navigationIcon = {
            IconButton(onClick = navigationIcon.onClick) {
                AsyncImage(
                    modifier = Modifier.size(24.dp),
                    model = navigationIcon.icon,
                    contentDescription = navigationIcon.description?.let { stringResource(it) },
                    colorFilter = ColorFilter.tint(buttonContentColor)
                )
            }
        },
        actions = {
            IconButton(onClick = actionIcon.onClick) {
                AsyncImage(
                    modifier = Modifier.size(24.dp),
                    model = actionIcon.icon,
                    contentDescription = actionIcon.description?.let { stringResource(it) },
                    colorFilter = ColorFilter.tint(buttonContentColor)
                )
            }
        }
    )
}

/**
 * 메인(스케줄)화면의 앱바
 *
 */
@Composable
fun PrimaryTopAppBar(
    title: String,
    onClickNavigationIcon: () -> Unit,
    actionIcon: TopAppBarActionIcon
) {
    TopAppBarBase(
        modifier = Modifier,
        title = title,
        titleStyle = H3,
        containerColor = PrimaryGreen300,
        titleContentColor = NeutralGray0,
        buttonContentColor = NeutralGray0,
        navigationIcon = if (LocalMainState.current) TopAppBarNavigationIcon.Drawer { onClickNavigationIcon() } else TopAppBarNavigationIcon.Back { onClickNavigationIcon() },
        actionIcon = actionIcon
    )
}

@Composable
fun SecondaryTopAppBar(
    title: String,
    onClickNavigationIcon: () -> Unit,
    actionIcon: TopAppBarActionIcon
) {
    TopAppBarBase(
        modifier = Modifier.drawBehind {
            drawLine(
                color = NeutralGray800,
                start = Offset(0f, size.height),
                end = Offset(size.width, size.height),
                strokeWidth = 1.dp.toPx()
            )
        },
        title = title,
        titleStyle = H3_m,
        containerColor = NeutralGray0,
        titleContentColor = NeutralGray800,
        buttonContentColor = NeutralGray800,
        navigationIcon = if (LocalMainState.current) TopAppBarNavigationIcon.Drawer { onClickNavigationIcon() } else TopAppBarNavigationIcon.Back { onClickNavigationIcon() },
        actionIcon = actionIcon
    )
}

@Composable
fun TertiaryTopAppBar(
    title: String,
    containerColor: Color,
    onClickNavigationIcon: () -> Unit,
    actionIcon: TopAppBarActionIcon
) {
    TopAppBarBase(
        modifier = Modifier.drawBehind {
            drawLine(
                color = containerColor,
                start = Offset(0f, size.height),
                end = Offset(size.width, size.height),
                strokeWidth = 1.dp.toPx()
            )
        },
        title = title,
        titleStyle = H3_m,
        containerColor = NeutralGray0,
        titleContentColor = NeutralGray800,
        buttonContentColor = NeutralGray800,
        navigationIcon = if (LocalMainState.current) TopAppBarNavigationIcon.Drawer { onClickNavigationIcon() } else TopAppBarNavigationIcon.Back { onClickNavigationIcon() },
        actionIcon = actionIcon
    )
}