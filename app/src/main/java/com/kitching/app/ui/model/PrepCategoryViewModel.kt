package com.kitching.app.ui.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitching.domain.AppResult
import com.kitching.domain.entities.PrepCategory
import com.kitching.domain.repository.PrepCategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PrepCategoryViewModel(
    private val prepCategoryRepository: PrepCategoryRepository
) : ViewModel() {

    private val _prepCategory = MutableStateFlow<AppResult<List<PrepCategory>>>(AppResult.Loading)
    val prepCategories get() = _prepCategory.asStateFlow()

    fun getPrepCategory(teamId: String) {
        viewModelScope.launch {
            prepCategoryRepository.getPrepCategory(teamId).collectLatest {
                _prepCategory.value = it
                Log.d("color - viewmodel", it.toString())
            }
        }
    }

    private val _prepCategoryResult = MutableStateFlow<AppResult<Boolean>>(AppResult.Loading)
    val prepCategoryResult get() = _prepCategoryResult.asStateFlow()

    fun createPrepCategory(teamId: String, categoryName: String, color: String) {
        viewModelScope.launch {
            prepCategoryRepository.createPrepCategory(teamId, categoryName, color).collectLatest {
                _prepCategoryResult.value = it
                getPrepCategory(teamId)
            }
        }
    }

    fun updatePrepCategory(categoryId: String, categoryName: String, color: String) {
        viewModelScope.launch {
            prepCategoryRepository.updatePrepCategory(categoryId, categoryName, color).collectLatest {
                _prepCategoryResult.value = it
            }
        }
    }

    fun deletePrepCategory(categoryId: String) {
        viewModelScope.launch {
            prepCategoryRepository.deletePrepCategory(categoryId).collectLatest {
                _prepCategoryResult.value = it
            }
        }
    }
}