package com.kitching.app.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.kitching.app.notification.RecipeNotificationChannelDef
import com.kitching.app.notification.values.RecipeNotification
import com.kitching.data.repository.RecipeRepositoryImpl
import com.kitching.domain.AppResult
import com.kitching.domain.entities.Ingredient
import com.kitching.domain.usecase.RecipeUploadServiceUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.json.Json

class RecipeUploadWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    private val recipeUploadServiceUseCase = RecipeUploadServiceUseCase(RecipeRepositoryImpl())
    private val notificationChannel = RecipeNotificationChannelDef()

    override suspend fun doWork(): Result = runCatching {
        setForeground(createForegroundInfo())

        val notification = notificationChannel.RecipeUploadNotification(
            context = applicationContext,
            recipeDataSize = 1
        )

        notification.showStartNotification()

        val imageDataString = inputData.getString(KEY_IMAGE_DATA)
        val imageData = imageDataString?.let {
            Json.decodeFromString<ByteArray?>(it)
        }
        val imageName = inputData.getString(KEY_IMAGE_NAME) ?: ""
        val recipeName = inputData.getString(KEY_RECIPE_NAME) ?: ""
        val recipeStepsString = inputData.getString(KEY_RECIPE_STEPS) ?: "[]"
        val recipeSteps = Json.decodeFromString<List<String>>(recipeStepsString)
        val teamId = inputData.getString(KEY_TEAM_ID) ?: ""
        val ingredientsString = inputData.getString(KEY_INGREDIENTS) ?: "[]"
        val ingredients = Json.decodeFromString<List<Ingredient>>(ingredientsString)

        recipeUploadServiceUseCase(
            imageData = imageData,
            imageName = imageName,
            recipeName = recipeName,
            steps = recipeSteps,
            teamId = teamId,
            ingredients = ingredients
        ).collectLatest { result ->
            when (result) {
                is AppResult.Initial -> {}

                is AppResult.Loading -> {
                    notification.showProgressNotification(0)
                }

                is AppResult.Success -> {
                    notification.showCompleteNotification()
                }

                is AppResult.Failure -> {
                    notification.showErrorNotification(result.exception.message.toString())

                    throw result.exception
                }
            }
        }

        notification.showCompleteNotification()

        Result.success()
    }.getOrElse { exception ->
        Result.failure(
            workDataOf(KEY_ERROR_MESSAGE to exception.message)
        )
    }

    private fun createForegroundInfo(): ForegroundInfo {
        notificationChannel.createChannel(applicationContext)

        return ForegroundInfo(
            RecipeNotification.RECIPE_UPLOAD_IN_PROGRESS_ID,
            notificationChannel.getInitialedNotification(applicationContext)
        )
    }

    companion object {
        const val KEY_IMAGE_DATA = "image_data"
        const val KEY_IMAGE_NAME = "image_name"
        const val KEY_RECIPE_NAME = "recipe_name"
        const val KEY_RECIPE_STEPS = "recipe_steps"
        const val KEY_TEAM_ID = "team_id"
        const val KEY_INGREDIENTS = "ingredients"
        const val KEY_ERROR_MESSAGE = "error_message"
    }
}