package com.kitching.domain

sealed class AppResult<out T> {
    object Loading : AppResult<Nothing>()
    data class Success<out T>(val data: T) : AppResult<T>()
    data class Failure(val exception: Throwable) : AppResult<Nothing>()
}