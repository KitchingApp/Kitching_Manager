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
    ): Flow<AppResult<Unit>>

    fun createRecipe(
        imageData: ByteArray?,
        imageName: String,
        recipeName: String,
        steps: List<String>,
        teamId: String,
        ingredients: List<Ingredient>
    ): Flow<AppResult<Unit>>

    fun deleteRecipe(recipeId: String): Flow<AppResult<Unit>>
}