package com.kitching.app.ui.item

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardColors
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.kitching.app.R
import com.kitching.app.ui.screen.recipe.innercontent.RecipeSheetInfo
import com.kitching.app.ui.theme.H3_m
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.NeutralGray500
import com.kitching.app.ui.theme.NeutralGray800
import com.kitching.app.ui.theme.PrimaryGreen300
import com.kitching.app.ui.theme.defaultPadding

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
            width = 2.dp,
            color = if (isAvailable) {
                if (isSelected) PrimaryGreen300 else NeutralGray800
            } else NeutralGray500
        ),
        onClick = {
            if (isAvailable) {
                isSelected = !isSelected
                onSelect()
            } else isExpanded = !isExpanded
        }
    ) {
        Column(
            modifier = Modifier.padding(defaultPadding)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${recipeSheetInfo.sheetName} - ${recipeSheetInfo.recipeName}",
                    style = H3_m,
                    color = if (isAvailable) {
                        if (isSelected) PrimaryGreen300 else NeutralGray800
                    } else NeutralGray500
                )
                if (isAvailable) {
                    AsyncImage(
                        modifier = Modifier.size(24.dp),
                        model = (R.drawable.icon_check),
                        contentDescription = "Checked recipe",
                        colorFilter = ColorFilter.tint(if (isSelected) PrimaryGreen300 else NeutralGray800)
                    )
                } else {
                    AsyncImage(
                        modifier = Modifier.size(24.dp),
                        model = if (isExpanded) R.drawable.icon_up else R.drawable.icon_down,
                        contentDescription = if (isExpanded) "card expanded" else "card collapsed",
                        colorFilter = ColorFilter.tint(NeutralGray500)
                    )
                }
            }
            if (!isAvailable) {
                Text(
                    text = "업로드 할 수 없습니다.",
                    color = NeutralGray500
                )
            }
            if (isExpanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "레시피 사진",
                            color = if (recipeSheetInfo.imageData !== null) PrimaryGreen300 else Color.Red
                        )
                        AsyncImage(
                            modifier = Modifier.size(24.dp),
                            model = if (recipeSheetInfo.imageData !== null) R.drawable.baseline_check_circle_outline_24 else R.drawable.round_error_outline_24,
                            contentDescription = if (recipeSheetInfo.imageData !== null) "no problem with image" else "problem with image",
                            colorFilter = ColorFilter.tint(if (recipeSheetInfo.imageData !== null) PrimaryGreen300 else Color.Red)
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "레시피 이름",
                            color = if (recipeSheetInfo.recipeName.isNotEmpty()) PrimaryGreen300 else Color.Red
                        )
                        AsyncImage(
                            modifier = Modifier.size(24.dp),
                            model = if (recipeSheetInfo.recipeName.isNotEmpty()) R.drawable.baseline_check_circle_outline_24 else R.drawable.round_error_outline_24,
                            colorFilter = ColorFilter.tint(if (recipeSheetInfo.recipeName.isNotEmpty()) PrimaryGreen300 else Color.Red),
                            contentDescription = if (recipeSheetInfo.recipeName.isNotEmpty()) "no problem with name" else "problem with name"
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "재료",
                            color = if (recipeSheetInfo.ingredients.isNotEmpty()) PrimaryGreen300 else Color.Red
                        )
                        AsyncImage(
                            modifier = Modifier.size(24.dp),
                            model = if (recipeSheetInfo.ingredients.isNotEmpty()) R.drawable.baseline_check_circle_outline_24 else R.drawable.round_error_outline_24,
                            colorFilter = ColorFilter.tint(if (recipeSheetInfo.ingredients.isNotEmpty()) PrimaryGreen300 else Color.Red),
                            contentDescription = if (recipeSheetInfo.ingredients.isNotEmpty()) "no problem with ingredients" else "problem with ingredients"
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "순서",
                            color = if (recipeSheetInfo.recipeSteps.isNotEmpty()) PrimaryGreen300 else Color.Red
                        )
                        AsyncImage(
                            modifier = Modifier.size(24.dp),
                            model = if (recipeSheetInfo.recipeSteps.isNotEmpty()) R.drawable.baseline_check_circle_outline_24 else R.drawable.round_error_outline_24,
                            colorFilter = ColorFilter.tint(if (recipeSheetInfo.recipeSteps.isNotEmpty()) PrimaryGreen300 else Color.Red),
                            contentDescription = if (recipeSheetInfo.recipeSteps.isNotEmpty()) "no problem with recipe steps" else "problem with recipe steps"
                        )
                    }
                }
            }
        }
    }
}