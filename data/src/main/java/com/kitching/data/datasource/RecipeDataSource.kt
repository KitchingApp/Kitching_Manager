package com.kitching.data.datasource

import com.kitching.data.dto.IngredientDTO
import com.kitching.data.dto.RecipeDTO

interface RecipeDataSource {
    suspend fun getRecipes(teamId: String): List<RecipeDTO>

    suspend fun getRecipeById(recipeId: String): RecipeDTO

    suspend fun updateRecipe(
        recipeId: String,
        name: String,
        steps: List<String>,
        ingredients: List<IngredientDTO>,
    )

    suspend fun createRecipe(
        imageData: ByteArray?,
        imageName: String,
        recipeName: String,
        steps: List<String>,
        teamId: String,
        ingredients: List<IngredientDTO>
    )
}