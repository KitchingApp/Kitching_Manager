package com.kitching.domain.usecase

import com.kitching.domain.AppResult
import com.kitching.domain.entities.Ingredient
import com.kitching.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow

class RecipeUploadServiceUseCase(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(
        imageData: ByteArray?,
        imageName: String,
        recipeName: String,
        steps: List<String>,
        teamId: String,
        ingredients: List<Ingredient>
    ): Flow<AppResult<Unit>> {
        return recipeRepository.createRecipe(
            imageData, imageName, recipeName, steps, teamId, ingredients
        )
    }
}