package com.kitching.data.repository

import com.kitching.data.datasource.OrderCategoryDataSource
import com.kitching.data.datasource.OrderCategoryDataSourceImpl
import com.kitching.domain.AppResult
import com.kitching.domain.entities.OrderCategory
import com.kitching.domain.repository.OrderCategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class OrderCategoryRepositoryImpl(
    private val orderCategoryDataSource: OrderCategoryDataSource = OrderCategoryDataSourceImpl(),
): OrderCategoryRepository {
    override fun getOrderCategory(teamId: String): Flow<AppResult<List<OrderCategory>>> = flow {
        emit(AppResult.Loading)
        val orderCategories = orderCategoryDataSource.getOrderCategories(teamId)
        if (orderCategories.isEmpty()) emit(AppResult.Success(emptyList()))
        else emit(AppResult.Success(
            orderCategories.map {
                OrderCategory(
                    categoryId = it.id,
                    categoryName = it.name,
                    color = it.color,
                )
            }
        ))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override fun createOrderCategory(
        teamId: String,
        categoryName: String,
        color: String
    ) = flow {
        emit(AppResult.Loading)
        emit(AppResult.Success(orderCategoryDataSource.createOrderCategory(teamId, categoryName, color)))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override fun deleteOrderCategory(categoryId: String) = flow {
        emit(AppResult.Loading)
        emit(AppResult.Success(orderCategoryDataSource.deleteOrderCategory(categoryId)))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override fun updateOrderCategory(
        categoryId: String,
        categoryName: String,
        color: String
    ) = flow {
        emit(AppResult.Loading)
        emit(AppResult.Success(orderCategoryDataSource.updateOrderCategory(categoryId, categoryName, color)))
    }.catch {
        emit(AppResult.Failure(it))
    }
}