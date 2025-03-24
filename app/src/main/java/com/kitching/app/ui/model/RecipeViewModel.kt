package com.kitching.app.ui.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitching.app.util.PreferencesDataStore
import com.kitching.domain.AppResult
import com.kitching.domain.entities.Ingredient
import com.kitching.domain.entities.Recipe
import com.kitching.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RecipeViewModel(
    private val recipeRepository: RecipeRepository,
    val dataStore: PreferencesDataStore,
) : ViewModel() {
    private val _recipeList = MutableStateFlow<AppResult<List<Recipe>>>(AppResult.Initial)
    val recipeList get() = _recipeList.asStateFlow()

    fun getRecipesByTeamId(teamId: String) {
        viewModelScope.launch {
            recipeRepository.getRecipes(teamId).collectLatest {
                _recipeList.value = it
            }
        }
    }

    private val _recipeDetail = MutableStateFlow<AppResult<Recipe>>(AppResult.Initial)
    val recipeDetail get() = _recipeDetail.asStateFlow()

    fun getRecipeById(recipeId: String) {
        viewModelScope.launch {
            recipeRepository.getRecipeById(recipeId).collectLatest {
                _recipeDetail.value = it
            }
        }
    }

    private val _updateResult = MutableStateFlow<AppResult<Unit>>(AppResult.Initial)
    val updateResult get() = _updateResult.asStateFlow()

    fun updateRecipe(
        recipeId: String,
        name: String,
        steps: List<String>,
        ingredients: List<Ingredient>,
    ) {
        viewModelScope.launch {
            recipeRepository.updateRecipe(recipeId, name, steps, ingredients).collectLatest {
                _updateResult.value = it
            }
        }
    }

    private val _createRecipeResult = MutableStateFlow<AppResult<Unit>>(AppResult.Initial)
    val createRecipeResult get() = _createRecipeResult.asStateFlow()

    fun createRecipe(
        imageData: ByteArray?,
        imageName: String,
        recipeName: String,
        steps: List<String>,
        teamId: String,
        ingredients: List<Ingredient>,
    ) {
        viewModelScope.launch {
            val ingMapList = ingredients.map { ing ->
                mapOf(
                    "id" to "",
                    "name" to ing.ingredientName,
                    "once" to ing.once.toString(),
                    "twice" to ing.twice.toString(),
                    "unit" to ing.unit
                )
            }

            recipeRepository.createRecipe(
                imageData, imageName, recipeName, steps, teamId, ingMapList
            ).collectLatest {
                _createRecipeResult.value = it
            }
        }
    }
}
