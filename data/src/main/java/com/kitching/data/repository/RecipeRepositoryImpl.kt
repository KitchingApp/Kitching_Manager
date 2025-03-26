package com.kitching.data.repository

import com.kitching.data.datasource.RecipeDataSource
import com.kitching.data.datasource.RecipeDataSourceImpl
import com.kitching.data.dto.IngredientDTO
import com.kitching.domain.AppResult
import com.kitching.domain.entities.Ingredient
import com.kitching.domain.entities.Recipe
import com.kitching.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class RecipeRepositoryImpl(
    private val recipeDataSource: RecipeDataSource = RecipeDataSourceImpl()
) : RecipeRepository {
    override fun getRecipes(teamId: String): Flow<AppResult<List<Recipe>>> = flow {
        emit(AppResult.Loading)
        emit(AppResult.Success(recipeDataSource.getRecipes(teamId).map { it.toDomain() }.toList()))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override fun getRecipeById(recipeId: String) = flow {
        emit(AppResult.Loading)
        emit(AppResult.Success(recipeDataSource.getRecipeById(recipeId).toDomain()))
    }.catch { emit(AppResult.Failure(it)) }

    override fun updateRecipe(
        recipeId: String,
        name: String,
        steps: List<String>,
        ingredients: List<Ingredient>,
    ) = flow {
        emit(AppResult.Loading)
        emit(AppResult.Success(recipeDataSource.updateRecipe(recipeId, name, steps, ingredients.map { ingredients ->
            IngredientDTO(
                id = ingredients.ingredientId,
                name = ingredients.ingredientName,
                once = ingredients.once,
                twice = ingredients.twice,
                unit = ingredients.unit
            )
        })))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override fun createRecipe(
        imageData: ByteArray?,
        imageName: String,
        recipeName: String,
        steps: List<String>,
        teamId: String,
        ingredients: List<Map<String, String>>,
    ) = flow {
        emit(AppResult.Loading)
        emit(AppResult.Success(recipeDataSource.createRecipe(
            imageData, imageName, recipeName, steps, teamId, ingredients
        )))
    }.catch {
        emit(AppResult.Failure(it))
    }
}