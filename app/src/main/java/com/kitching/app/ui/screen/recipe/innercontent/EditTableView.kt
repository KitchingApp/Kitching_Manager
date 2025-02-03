package com.kitching.app.ui.screen.recipe.innercontent

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kitching.app.ui.screen.recipe.Ingredient
import com.kitching.app.ui.theme.Caption1_R
import com.kitching.app.ui.theme.NeutralGray500
import com.kitching.app.ui.theme.PrimaryGreen50


@Composable
fun EditIngredientsTable(
    ingredients: List<Ingredient>,
    onIngredientsChange: (List<Ingredient>) -> Unit
) {

    var updatedIngredients by remember { mutableStateOf(ingredients) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, NeutralGray500)
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(PrimaryGreen50)
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = "X1", style = Caption1_R, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
            Text(text = "X2", style = Caption1_R, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
            Text(text = "Unit", style = Caption1_R, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
            Text(text = "재료", style = Caption1_R, textAlign = TextAlign.Center, modifier = Modifier.weight(2f))
        }

        Divider(color = NeutralGray500, thickness = 1.dp)

        updatedIngredients.forEachIndexed { index, ingredient ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, NeutralGray500)
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BasicTextField(
                    value = ingredient.once.toString(),
                    onValueChange = { value ->
                        updatedIngredients = updatedIngredients.toMutableList().apply {
                            this[index] = this[index].copy(once = value.toIntOrNull() ?: 0)
                        }
                        onIngredientsChange(updatedIngredients)
                    },
                    modifier = Modifier
                        .weight(1f),
                    textStyle = TextStyle(textAlign = TextAlign.Center)
                )

                BasicTextField(
                    value = ingredient.twice.toString(),
                    onValueChange = { value ->
                        updatedIngredients = updatedIngredients.toMutableList().apply {
                            this[index] = this[index].copy(twice = value.toIntOrNull() ?: 0)
                        }
                        onIngredientsChange(updatedIngredients)
                    },
                    modifier = Modifier
                        .weight(1f),
                    textStyle = TextStyle(textAlign = TextAlign.Center)
                )

                BasicTextField(
                    value = ingredient.each,
                    onValueChange = { value ->
                        updatedIngredients = updatedIngredients.toMutableList().apply {
                            this[index] = this[index].copy(each = value)
                        }
                        onIngredientsChange(updatedIngredients)
                    },
                    modifier = Modifier
                        .weight(1f),
                    textStyle = TextStyle(textAlign = TextAlign.Center)
                )

                BasicTextField(
                    value = ingredient.name,
                    onValueChange = { value ->
                        updatedIngredients = updatedIngredients.toMutableList().apply {
                            this[index] = this[index].copy(name = value)
                        }
                        onIngredientsChange(updatedIngredients)
                    },
                    modifier = Modifier
                        .weight(2f),
                    textStyle = TextStyle(textAlign = TextAlign.Center)
                )
            }
        }
    }
}

@Composable
fun EditStepTable(
    steps: List<String>,
    onStepsChange: (List<String>) -> Unit
) {
    var updatedSteps by remember { mutableStateOf(steps) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, NeutralGray500)
            .background(Color.White)
    ) {
        updatedSteps.forEachIndexed { index, step ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "${index + 1}.",
                    style = Caption1_R,
                    modifier = Modifier.padding(start = 50.dp)
                )

                BasicTextField(
                    value = step,
                    onValueChange = { value ->
                        updatedSteps = updatedSteps.toMutableList().apply {
                            this[index] = value
                        }
                        onStepsChange(updatedSteps)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp),
                    textStyle = TextStyle(textAlign = TextAlign.Center)
                )
            }
            Divider(color = NeutralGray500, thickness = 1.dp)
        }
    }
}