package com.kitching.domain.usecase

import com.kitching.domain.AppResult
import com.kitching.domain.entities.Ingredient
import com.kitching.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest

class RecipeUploadServiceUseCase(
    private val recipeRepository: RecipeRepository
) {
    private var _result = MutableStateFlow<AppResult<Unit>>(AppResult.Initial)
    val result get() = _result.asStateFlow()

    suspend fun uploadRecipe(
        imageData: ByteArray?,
        imageName: String,
        recipeName: String,
        steps: List<String>,
        teamId: String,
        ingredients: List<Ingredient>
    ) {
        recipeRepository.createRecipe(
            imageData, imageName, recipeName, steps, teamId, ingredients
        ).collectLatest { _result.value = it }
    }
}