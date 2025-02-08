package com.kitching.domain.repository

import com.kitching.domain.AppResult
import com.kitching.domain.entities.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getRecipes(teamId: String): Flow<AppResult<List<Recipe>>>

    fun getRecipeById(recipeId: String): Flow<AppResult<Recipe>>
}