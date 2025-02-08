package com.kitching.data.repository

import android.util.Log
import com.kitching.data.datasource.RecipeDataSource
import com.kitching.data.datasource.RecipeDataSourceImpl
import com.kitching.domain.AppResult
import com.kitching.domain.entities.Recipe
import com.kitching.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class RecipeRepositoryImpl(
    private val recipeDataSource: RecipeDataSource = RecipeDataSourceImpl()
): RecipeRepository {
    override fun getRecipes(teamId: String): Flow<AppResult<List<Recipe>>> = flow {
        emit(AppResult.Loading)
        val recipes = recipeDataSource.getRecipes(teamId).map { it.toDomain() }.toList()
        Log.d("RecipeRepository", "Fetched recipes: $recipes")
        emit(AppResult.Success(recipes))
    }.catch {
        Log.e("RecipeRepository", "Error fetching recipes", it)
        emit(AppResult.Failure(it))
    }

    override fun getRecipeById(recipeId: String): Flow<AppResult<Recipe>> = flow {
        emit(AppResult.Loading)
        val recipe = recipeDataSource.getRecipeById(recipeId).toDomain()
        emit(AppResult.Success(recipe))
    }.catch { emit(AppResult.Failure(it)) }
}