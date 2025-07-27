package com.kitching.app.ui.screen.recipe.innercontent

import com.kitching.app.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kitching.app.navgraph.IngredientItem
import com.kitching.app.ui.theme.BlueColor200
import com.kitching.app.ui.theme.Body1_m
import com.kitching.app.ui.theme.Caption1_R
import com.kitching.app.ui.theme.H4_m
import com.kitching.app.ui.theme.NeutralGray300
import com.kitching.app.ui.theme.NeutralGray500
import com.kitching.app.ui.theme.NeutralGray800
import com.kitching.app.ui.theme.PrimaryGreen300
import com.kitching.app.ui.theme.PrimaryGreen50

@Composable
fun CreateIngredientsTable(
    ingredients: List<IngredientItem>,
    onIngredientsChange: (List<IngredientItem>) -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Text(
            text = "재료",
            style = H4_m,
            color = NeutralGray800
        )

        Spacer(modifier = Modifier.height(8.dp))

        /** 테이블 헤더 */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(PrimaryGreen50)
                .border(1.dp, NeutralGray500)
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("X1", "X2", "Unit", "재료").forEach {
                Text(
                    text = it,
                    style = Caption1_R,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(if (it == "재료") 2f else 1f)
                )
            }
        }

        /** 재료 입력 필드 */
        ingredients.forEachIndexed { index, ingredient ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                listOf(
                    ingredient.once.takeIf { it > -1 }?.toString() ?: "",
                    ingredient.twice.takeIf { it > -1 }?.toString() ?: "",
                    ingredient.unit,
                    ingredient.ingredientName
                ).forEachIndexed { columnIndex, value ->
                    BasicTextField(
                        value = value,
                        onValueChange = { newValue ->
                            val updatedList = ingredients.toMutableList().apply {
                                this[index] = when (columnIndex) {
                                    0 -> this[index].copy(once = newValue.toIntOrNull() ?: -1)
                                    1 -> this[index].copy(twice = newValue.toIntOrNull() ?: -1)
                                    2 -> this[index].copy(unit = newValue)
                                    else -> this[index].copy(ingredientName = newValue)
                                }
                            }
                            onIngredientsChange(updatedList)
                        },
                        textStyle = Caption1_R.copy(color = NeutralGray800, textAlign = TextAlign.Center),
                        singleLine = true,
                        modifier = Modifier
                            .weight(if (columnIndex == 3) 2f else 1f)
                            .border(1.dp, BlueColor200)
                            .fillMaxSize()
                            .padding(vertical = 13.dp),
                    )
                }
            }
        }

        /** + 버튼 (재료 추가) */
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            IconButton(onClick = {
                onIngredientsChange(ingredients + IngredientItem("", "", -1, -1, ""))
            }) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.add_button),
                    contentDescription = "재료 추가",
                    tint = PrimaryGreen300,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}

@Composable
fun CreateStepTable(
    steps: List<String>,
    onStepsChange: (List<String>) -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Text(
            text = "레시피 순서",
            style = H4_m,
            color = NeutralGray800,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        /** 재료 입력 필드 */
        Column(
            modifier = Modifier
                .fillMaxSize()
                .border(1.dp, NeutralGray500)
        ) {
            steps.forEachIndexed { index, step ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${index + 1}.",
                        style = Body1_m,
                        color = NeutralGray800,
                        modifier = Modifier.padding(end = 10.dp)
                    )

                    BasicTextField(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        value = step,
                        onValueChange = { newValue ->
                            val updatedSteps = steps.toMutableList().apply {
                                this[index] = newValue
                            }
                            onStepsChange(updatedSteps)
                        },
                        textStyle = Body1_m.copy(color = NeutralGray800),
                        decorationBox = { innerTextField ->
                            if (step.isEmpty()) {
                                Text(
                                    text = "레시피 순서를 입력해주세요.",
                                    style = Body1_m.copy(color = NeutralGray300)
                                )
                            }
                            innerTextField()
                        }
                    )
                }
            }
        }

        /** + 버튼 (순서 추가) */
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            IconButton(onClick = {
                onStepsChange(steps + "")
            }) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.add_button),
                    contentDescription = "레시피 순서 추가",
                    tint = PrimaryGreen300,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}
