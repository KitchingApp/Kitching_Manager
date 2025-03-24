package com.kitching.app.ui.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.kitching.app.common.CommonState
import com.kitching.app.ui.theme.Caption1_R
import com.kitching.app.ui.theme.NeutralGray800
import com.kitching.app.ui.theme.ShadowColor
import com.kitching.app.util.dropShadow
import com.kitching.domain.entities.Recipe

@Composable
fun RecipeItem(recipe: Recipe, commonState: CommonState) {
    Card(
        modifier = Modifier
            .size(width = 150.dp, height = 188.dp)
            .dropShadow(
                RoundedCornerShape(8.dp),
                ShadowColor,
                blur = 12.dp
            )
            .clickable { commonState.navController.navigate("detail/${recipe.recipeId}") },
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White, RoundedCornerShape(8.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = recipe.picture,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier.fillMaxWidth().height(28.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = recipe.recipeName,
                    style = Caption1_R.copy(color = NeutralGray800),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}