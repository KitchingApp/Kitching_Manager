package com.kitching.app.ui.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitching.domain.AppResult
import com.kitching.domain.entities.Prep
import com.kitching.domain.repository.PrepRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PrepViewModel(
    private val prepRepository: PrepRepository
) : ViewModel() {

    private val _prepList = MutableStateFlow<AppResult<List<Prep>>>(AppResult.Loading)
    val prepList get() = _prepList.asStateFlow()

    fun getPrepList(categoryId: String) {
        viewModelScope.launch {
            prepRepository.getPrepList(categoryId).collectLatest {
                _prepList.value = it
                Log.d("PrepViewModel", _prepList.value.toString())
            }
        }
    }

    private val _prepResult = MutableStateFlow<AppResult<Boolean>>(AppResult.Loading)
    val prepResult get() = _prepResult.asStateFlow()

    fun createPrep(categoryId: String, name: String) {
        viewModelScope.launch {
            prepRepository.createPrep(categoryId, name).collectLatest {
                _prepResult.value = it
            }
        }
    }

    fun updatePrep(prepId: String, name: String) {
        viewModelScope.launch {
            prepRepository.updatePrep(prepId, name).collectLatest {
                _prepResult.value = it
            }
        }
    }

    fun deletePrep(prepId: String) {
        viewModelScope.launch {
            prepRepository.deletePrep(prepId).collectLatest {
                _prepResult.value = it
            }
        }
    }
}