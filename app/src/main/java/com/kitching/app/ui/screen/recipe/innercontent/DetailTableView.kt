package com.kitching.app.ui.screen.recipe.innercontent

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kitching.app.ui.theme.Caption1_R
import com.kitching.app.ui.theme.NeutralGray500
import com.kitching.app.ui.theme.PrimaryGreen50
import com.kitching.domain.entities.Ingredient

@Composable
fun IngredientsTable(ingredients: List<Ingredient>) {
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

        ingredients.forEach { ingredient ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = "${ingredient.once}", style = Caption1_R, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                Text(text = "${ingredient.twice}", style = Caption1_R, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                Text(text = ingredient.unit, style = Caption1_R, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                Text(text = ingredient.ingredientName, style = Caption1_R, textAlign = TextAlign.Center, modifier = Modifier.weight(2f))

            }
                Divider(color = NeutralGray500, thickness = 1.dp)
        }
    }
}

@Composable
fun StepTable(steps: List<String>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, NeutralGray500)
            .background(Color.White)
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            steps.forEachIndexed { index, step ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Text(
                        text = "${index + 1}.",
                        style = Caption1_R,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = step,
                        style = Caption1_R
                    )
                }
            }
        }
    }
}