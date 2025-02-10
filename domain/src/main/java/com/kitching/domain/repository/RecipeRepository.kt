package com.kitching.domain.repository

import com.kitching.domain.AppResult
import com.kitching.domain.entities.Ingredient
import com.kitching.domain.entities.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getRecipes(teamId: String): Flow<AppResult<List<Recipe>>>

    fun getRecipeById(recipeId: String): Flow<AppResult<Recipe>>

    fun updateRecipe(
        recipeId: String,
        name: String,
        steps: List<String>,
        ingredients: List<Ingredient>,
    ): Flow<AppResult<Boolean>>

    fun uploadImage(imageData: ByteArray, imageName: String): Flow<AppResult<String>>

    fun saveRecipe(
        name: String,
        picture: String,
        steps: List<String>,
        teamId: String,
    ): Flow<AppResult<String>>

    fun saveIngredients(
        recipeId: String,
        ingredients: List<Map<String, String>>,
    ): Flow<AppResult<Boolean>>
}