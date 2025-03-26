package com.kitching.app.ui.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitching.domain.AppResult
import com.kitching.domain.entities.Order
import com.kitching.domain.repository.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class OrderViewModel(
    private val orderRepository: OrderRepository
) : ViewModel() {

    private val _orders = MutableStateFlow<AppResult<List<Order>>>(AppResult.Initial)
    val orders get() = _orders
        .asStateFlow()

    fun getOrderList(categoryId: String) {
        viewModelScope.launch {
            orderRepository.getOrderList(categoryId).collectLatest {
                _orders.value = it
            }
        }
    }

    private var _orderResult = MutableStateFlow<AppResult<Unit>>(AppResult.Initial)
    val orderResult get() = _orderResult.asStateFlow()

    fun createOrder(categoryId: String, orderName: String) {
        viewModelScope.launch {
            orderRepository.createOrder(categoryId, orderName).collectLatest {
                _orderResult.value = it
            }
        }
    }

    fun deleteOrder(orderId: String) {
        viewModelScope.launch {
            orderRepository.deleteOrder(orderId).collectLatest {
                _orderResult.value = it
            }
        }
    }

    fun updateOrder(orderId: String, orderName: String) {
        viewModelScope.launch {
            orderRepository.updateOrder(orderId, orderName).collectLatest {
                _orderResult.value = it
            }
        }
    }
}