package com.kitching.app.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.kitching.app.MainActivity
import com.kitching.app.R
import com.kitching.app.navgraph.ScreenRouteDef
import com.kitching.app.notification.values.RecipeNotification
import com.kitching.app.service.NAVIGATE_TO_RECIPE_MAIN_KEY


/**
 * 레시피 업로드 포그라운드 서비스를 위한 노티피케이션 채널
 *
 */
class RecipeNotificationChannelDef() : NotificationChannelDef(
    channelId = RecipeNotification.CHANNEL_ID,
    importance = NotificationManager.IMPORTANCE_HIGH,
    channelName = R.string.recipe_upload_service_notification_channel_name,
    channelDescription = R.string.recipe_upload_service_notification_channel_description
) {
    fun getInitialedNotification(context: Context) =
        createBasicNotificationBuilder(
            context = context,
            title = context.getString(R.string.recipe_upload_service_start_notification_title),
            text = context.getString(R.string.recipe_upload_service_start_notification_content)
        )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

    /**
     * 레시피를 업로드하는 노티피케이션 클래스
     *
     * @property context
     */
    inner class RecipeUploadNotification(
        private val context: Context,
        private val recipeDataSize: Int
    ) {
        private val notificationId = RecipeNotification.RECIPE_UPLOAD_ID

        private val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        /** 진행률 표시기 노티피케이션 */
        private val progressNotificationBuilder = createBasicNotificationBuilder(
            context = context,
            title = context.getString(R.string.recipe_upload_service_in_progress_notification_title),
            text = context.getString(R.string.recipe_upload_service_start_notification_content)
        ).setProgress(recipeDataSize, 0, true)

        /** 업로드 시작 노티피케이션 띄우기 */
        fun showStartNotification() {
            notificationManager.notify(
                notificationId,
                getInitialedNotification(context)
            )
        }

        /** 진행률 표시하기 */
        fun showProgressNotification(progress: Int) {
            notificationManager.notify(
                notificationId,
                progressNotificationBuilder
                    .setProgress(recipeDataSize, progress, true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .build()
            )
        }

        /** 업로드 완료 노티피케이션 띄우기 */
        fun showCompleteNotification() {
            // 클릭 시 메인 액티비티로 이동하는 PendingIntent
            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                Intent(context, MainActivity::class.java).apply {
                    putExtra(NAVIGATE_TO_RECIPE_MAIN_KEY, ScreenRouteDef.RecipeGraph.route)
                },
                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
            )

            notificationManager.notify(
                notificationId,
                createBasicNotificationBuilder(
                    context = context,
                    title = context.getString(R.string.recipe_upload_service_complete_notification_title),
                    text = context.getString(R.string.recipe_upload_service_complete_notification_content)
                )
                    .setContentIntent(pendingIntent)
                    .build()
            )
        }
    }
}