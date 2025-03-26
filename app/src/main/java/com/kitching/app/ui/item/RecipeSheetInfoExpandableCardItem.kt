package com.kitching.app.ui.item

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.kitching.app.R
import com.kitching.app.ui.screen.recipe.innercontent.RecipeSheetInfo
import com.kitching.app.ui.theme.Body1_m
import com.kitching.app.ui.theme.Caption1_m
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.NeutralGray800
import com.kitching.app.ui.theme.PrimaryGreen300
import com.kitching.app.ui.theme.SecondaryRed300
import com.kitching.app.ui.theme.SecondaryRed600

@Composable
fun RecipeSheetInfoExpandableCardItem(
    recipeSheetInfo: RecipeSheetInfo,
    onSelect: () -> Unit
) {
    val isAvailable = (
            recipeSheetInfo.recipeName.isNotEmpty() &&
                    recipeSheetInfo.imageData !== null &&
                    recipeSheetInfo.ingredients.isNotEmpty() &&
                    recipeSheetInfo.recipeSteps.isNotEmpty()
            )
    var isSelected by remember { mutableStateOf(false) }
    var isExpanded by remember { mutableStateOf(false) }

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardColors(
            containerColor = NeutralGray0,
            contentColor = NeutralGray800,
            disabledContainerColor = NeutralGray0,
            disabledContentColor = NeutralGray800
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (isAvailable) {
                if (isSelected) PrimaryGreen300 else NeutralGray800
            } else SecondaryRed300
        ),
        onClick = {
            if (isAvailable) {
                isSelected = !isSelected
                onSelect()
            } else isExpanded = !isExpanded
        }
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${recipeSheetInfo.sheetName} - ${recipeSheetInfo.recipeName}",
                    style = Body1_m,
                    color = if (isAvailable) {
                        if (isSelected) PrimaryGreen300 else NeutralGray800
                    } else SecondaryRed600
                )
                AsyncImage(
                    modifier = Modifier.size(24.dp),
                    model = when {
                        isAvailable -> R.drawable.icon_check
                        isExpanded -> R.drawable.icon_up
                        else -> R.drawable.icon_down
                    },
                    contentDescription = when {
                        isAvailable -> "Checked recipe"
                        isExpanded -> "card expanded"
                        else -> "card collapsed"
                    },
                    colorFilter = ColorFilter.tint(
                        when {
                            isAvailable -> if (isSelected) PrimaryGreen300 else NeutralGray800
                            else -> SecondaryRed300
                        }
                    )
                )
            }
            if (!isAvailable) {
                Text(
                    text = "업로드 할 수 없습니다.",
                    style = Caption1_m,
                    color = SecondaryRed600
                )
            }
            if (isExpanded) {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = SecondaryRed300,
                    thickness = 1.dp
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    RecipeFormStateRow(
                        correctCondition = recipeSheetInfo.imageData !== null,
                        targetElement = "레시피 사진"
                    )
                    RecipeFormStateRow(
                        correctCondition = recipeSheetInfo.recipeName.isNotEmpty(),
                        targetElement = "레시피 이름"
                    )
                    RecipeFormStateRow(
                        correctCondition = recipeSheetInfo.ingredients.isNotEmpty(),
                        targetElement = "재료"
                    )
                    RecipeFormStateRow(
                        correctCondition = recipeSheetInfo.recipeSteps.isNotEmpty(),
                        targetElement = "순서"
                    )
                }
            }
        }
    }
}

@Composable
fun RecipeFormStateRow(correctCondition: Boolean, targetElement: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = targetElement,
            style = Caption1_m,
            color = if (correctCondition) PrimaryGreen300 else SecondaryRed600
        )
        AsyncImage(
            modifier = Modifier.size(10.dp),
            model = if (correctCondition) R.drawable.icon_check_circle else R.drawable.icon_error_circle,
            colorFilter = ColorFilter.tint(if (correctCondition) PrimaryGreen300 else SecondaryRed600),
            contentDescription = null
        )
    }
}