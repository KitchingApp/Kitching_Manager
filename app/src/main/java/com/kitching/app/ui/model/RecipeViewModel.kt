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

    private val _updateResult = MutableStateFlow<AppResult<Boolean>>(AppResult.Initial)
    val updateResult get() = _updateResult.asStateFlow()

    fun updateRecipe(recipeId: String, name: String, steps: List<String>, ingredients: List<Ingredient>) {
        viewModelScope.launch {
            recipeRepository.updateRecipe(recipeId, name, steps, ingredients).collectLatest {
                _updateResult.value = it
            }
        }
    }

    private val _uploadImageResult = MutableStateFlow<AppResult<String>>(AppResult.Initial)
    val uploadImageResult get() = _uploadImageResult.asStateFlow()

    private val _saveRecipeResult = MutableStateFlow<AppResult<String>>(AppResult.Initial)
    val saveRecipeResult get() = _saveRecipeResult.asStateFlow()

    private val _saveIngredientsResult = MutableStateFlow<AppResult<Boolean>>(AppResult.Initial)
    val saveIngredientsResult get() = _saveIngredientsResult.asStateFlow()

    /** ByteArray로 받은 이미지를 업로드 */
    fun uploadImage(imageData: ByteArray, imageName: String) {
        viewModelScope.launch {
            recipeRepository.uploadImage(imageData, imageName).collectLatest {
                _uploadImageResult.value = it
            }
        }
    }

    /** 레시피 저장 */
    fun saveRecipe(name: String, picture: String, steps: List<String>, teamId: String) {
        viewModelScope.launch {
            recipeRepository.saveRecipe(name, picture, steps, teamId).collectLatest {
                _saveRecipeResult.value = it
            }
        }
    }

    /** 재료 저장 */
    fun saveIngredients(recipeId: String, ingredients: List<Map<String, String>>) {
        viewModelScope.launch {
            recipeRepository.saveIngredients(recipeId, ingredients).collectLatest {
                _saveIngredientsResult.value = it
            }
        }
    }

    fun createRecipe(
        imageData: ByteArray?,
        imageName: String,
        recipeName: String,
        steps: List<String>,
        teamId: String,
        ingredients: List<Ingredient>
    ) {
        viewModelScope.launch {
            // 1) 이미지 업로드
            val pictureUrl = if (imageData != null) {
                var tempUrl: String? = null

                uploadImage(imageData, imageName)

                val result = _uploadImageResult.value

                when(result) {
                    is AppResult.Success -> result.data
                    else -> ""
                }
                tempUrl ?: ""
            } else {
                ""
            }

            // 2) 레시피 저장
            var newRecipeId: String? = null

            saveRecipe(recipeName, pictureUrl, steps, teamId) // <-- 개별 메서드

            val result = _saveRecipeResult.value

            when(result) {
                is AppResult.Success -> newRecipeId = result.data
                else -> {  }
            }

            val recipeId = newRecipeId ?: return@launch

            // 3) 재료 저장
            val ingredientMaps = ingredients.map { ing ->
                mapOf(
                    "id" to "",
                    "name" to ing.ingredientName,
                    "once" to ing.once.toString(),
                    "twice" to ing.twice.toString(),
                    "unit" to ing.unit
                )
            }

            saveIngredients(recipeId, ingredientMaps)
        }
    }
}
