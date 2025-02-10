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
    ): Boolean

    suspend fun uploadImageToStorage(imageData: ByteArray, imageName: String): Result<String>

    suspend fun saveRecipe(
        name: String,
        picture: String,
        steps: List<String>,
        teamId: String,
    ): Result<String>

    suspend fun saveIngredients(
        recipeId: String,
        ingredients: List<Map<String, String>>,
    ): Boolean
}