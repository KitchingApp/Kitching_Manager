package com.kitching.app.ui.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitching.app.util.PreferencesDataStore
import com.kitching.domain.AppResult
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
}