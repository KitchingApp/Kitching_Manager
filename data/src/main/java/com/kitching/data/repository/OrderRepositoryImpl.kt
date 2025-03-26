package com.kitching.data.repository

import com.kitching.data.datasource.OrderDataSource
import com.kitching.data.datasource.OrderDataSourceImpl
import com.kitching.domain.AppResult
import com.kitching.domain.entities.Order
import com.kitching.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class OrderRepositoryImpl(
    private val orderDataSource: OrderDataSource = OrderDataSourceImpl()
) : OrderRepository {
    override fun getOrderList(categoryId: String): Flow<AppResult<List<Order>>> = flow {
        emit(AppResult.Loading)
        val orderList = orderDataSource.getOrderList(categoryId)
        if (orderList.isEmpty()) emit(AppResult.Success(emptyList()))
        else emit(AppResult.Success(orderList.map {
            Order(
                categoryId = it.categoryId,
                orderId = it.id,
                orderName = it.name
            )
        }))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override fun createOrder(categoryId: String, orderName: String) =
        flow {
            emit(AppResult.Loading)
            emit(AppResult.Success(orderDataSource.createOrder(categoryId, orderName)))
        }.catch {
            emit(AppResult.Failure(it))
        }

    override fun deleteOrder(orderId: String) = flow {
        emit(AppResult.Loading)
        emit(AppResult.Success(orderDataSource.deleteOrder(orderId)))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override fun updateOrder(orderId: String, orderName: String) =
        flow {
            emit(AppResult.Loading)
            emit(AppResult.Success(orderDataSource.updateOrder(orderId, orderName)))
        }.catch {
            emit(AppResult.Failure(it))
        }
}