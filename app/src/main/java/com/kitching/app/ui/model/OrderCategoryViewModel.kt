package com.kitching.app.ui.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitching.domain.AppResult
import com.kitching.domain.entities.OrderCategory
import com.kitching.domain.repository.OrderCategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class OrderCategoryViewModel(
    private val orderCategoryRepository: OrderCategoryRepository
) : ViewModel() {

    private val _orderCategories = MutableStateFlow<AppResult<List<OrderCategory>>>(AppResult.Initial)
    val orderCategories get() = _orderCategories.asStateFlow()

    fun getOrderCategory(teamId: String) {
        viewModelScope.launch {
            orderCategoryRepository.getOrderCategory(teamId).collectLatest {
                _orderCategories.value = it
            }
        }
    }

    private val _orderCategoryResult = MutableStateFlow<AppResult<Unit>>(AppResult.Initial)
    val orderCategoryResult get() = _orderCategoryResult
        .asStateFlow()

    fun createOrderCategory(teamId: String, categoryName: String, color: String) {
        viewModelScope.launch {
            orderCategoryRepository.createOrderCategory(teamId, categoryName, color).collectLatest {
                _orderCategoryResult.value = it
                getOrderCategory(teamId)
            }
        }
    }

    fun updateOrderCategory(categoryId: String, categoryName: String, color: String) {
        viewModelScope.launch {
            orderCategoryRepository.updateOrderCategory(categoryId, categoryName, color).collectLatest {
                _orderCategoryResult.value = it
            }
        }
    }

    fun deleteOrderCategory(categoryId: String) {
        viewModelScope.launch {
            orderCategoryRepository.deleteOrderCategory(categoryId).collectLatest {
                _orderCategoryResult.value = it
            }
        }
    }
}