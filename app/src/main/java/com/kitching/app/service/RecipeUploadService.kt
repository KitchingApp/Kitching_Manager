package com.kitching.app.service

import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.kitching.app.navgraph.RecipeServiceItem
import com.kitching.app.notification.RecipeNotificationChannelDef
import com.kitching.app.notification.values.RecipeNotification
import com.kitching.data.repository.RecipeRepositoryImpl
import com.kitching.domain.AppResult
import com.kitching.domain.usecase.RecipeUploadServiceUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.io.File

class RecipeUploadService : Service() {
    private val recipeUploadServiceUseCase = RecipeUploadServiceUseCase(RecipeRepositoryImpl())
    private var serviceScope = CoroutineScope(Dispatchers.IO)
    private val notiChannel = RecipeNotificationChannelDef()

    private fun initForeground() {
        notiChannel.createChannel(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(
                RecipeNotification.RECIPE_UPLOAD_IN_PROGRESS_ID,
                notiChannel.getInitialedNotification(this),
                ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
            )
        } else {
            startForeground(
                RecipeNotification.RECIPE_UPLOAD_IN_PROGRESS_ID,
                notiChannel.getInitialedNotification(this)
            )
        }

    }

    override fun onCreate() {
        super.onCreate()
        initForeground()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        initForeground()

        intent.data?.let { uri ->
            val takeFlags =
                flags and (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            contentResolver.takePersistableUriPermission(uri, takeFlags)
            contentResolver.openInputStream(uri).use { inputStream ->
                val recipeData = Json.decodeFromString<RecipeServiceItem>(
                    inputStream!!.bufferedReader().use { it.readText() })

                val notification = notiChannel.RecipeUploadNotification(
                    context = this@RecipeUploadService,
                    recipeDataSize = recipeData.recipes.size
                )

                serviceScope.launch {
                    notification.showStartNotification()

                    recipeData.recipes.forEachIndexed { index, recipe ->
                        recipeUploadServiceUseCase(
                            imageData = recipe.imageData,
                            imageName = recipe.imageName,
                            recipeName = recipe.recipeName,
                            steps = recipe.recipeSteps,
                            teamId = recipeData.teamId,
                            ingredients = recipe.ingredients.map { ingredientData -> ingredientData.toDomain() }
                        ).collectLatest { result ->
                            when (result) {
                                is AppResult.Success -> {
                                    notification.showProgressNotification(index)
                                }

                                // 추후 추가하기
                                is AppResult.Failure -> {}
                                
                                else -> {}
                            }
                        }
                    }

                    stopForeground(STOP_FOREGROUND_REMOVE)
                    
                    notification.showCompleteNotification()

                    uri.path?.let { path -> File(path).delete() }
                }
            }
        }


        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        if (serviceScope.isActive) {
            serviceScope.cancel()
        }
        stopSelf()
    }
}