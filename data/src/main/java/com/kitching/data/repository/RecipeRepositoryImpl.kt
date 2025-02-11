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
): RecipeRepository {
    override fun getRecipes(teamId: String): Flow<AppResult<List<Recipe>>> = flow {
        emit(AppResult.Loading)
        val recipes = recipeDataSource.getRecipes(teamId).map { it.toDomain() }.toList()
        emit(AppResult.Success(recipes))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override fun getRecipeById(recipeId: String): Flow<AppResult<Recipe>> = flow {
        emit(AppResult.Loading)
        val recipe = recipeDataSource.getRecipeById(recipeId).toDomain()
        emit(AppResult.Success(recipe))
    }.catch { emit(AppResult.Failure(it)) }

    override fun updateRecipe(
        recipeId: String,
        name: String,
        steps: List<String>,
        ingredients: List<Ingredient>,
    ): Flow<AppResult<Boolean>> = flow {
        emit(AppResult.Loading)

        // Ingredient -> IngredientDTO
        val ingDtoList = ingredients.map { ingredients ->
            IngredientDTO(
                id = ingredients.ingredientId,
                name = ingredients.ingredientName,
                once = ingredients.once,
                twice = ingredients.twice,
                unit = ingredients.unit
            )
        }

        val isSuccess = recipeDataSource.updateRecipe(recipeId, name, steps, ingDtoList)

        if (isSuccess) {
            emit(AppResult.Success(true))
        } else {
            emit(AppResult.Failure(Exception()))
        }

    }.catch { e ->
        emit(AppResult.Failure(e))
    }

    override fun createRecipe(
        imageData: ByteArray?,
        imageName: String,
        recipeName: String,
        steps: List<String>,
        teamId: String,
        ingredients: List<Map<String, String>>,
    ): Flow<AppResult<Boolean>> = flow {
        emit(AppResult.Loading)

        val isSuccess = recipeDataSource.createRecipe(
            imageData, imageName, recipeName, steps, teamId, ingredients
        )

        if (isSuccess) {
            emit(AppResult.Success(true))
        } else {
            emit(AppResult.Failure(Exception()))
        }
    }.catch { e ->
        emit(AppResult.Failure(e))
    }
}