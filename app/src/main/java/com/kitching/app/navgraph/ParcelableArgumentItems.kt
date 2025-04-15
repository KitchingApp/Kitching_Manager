package com.kitching.app.navgraph

import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.kitching.app.util.customFormat
import com.kitching.domain.entities.Ingredient
import com.kitching.domain.entities.Member
import com.kitching.domain.entities.Recipe
import com.kitching.domain.entities.ScheduleTime
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalTime

@Serializable
@Parcelize
data class CategoryItem(
    val categoryId: String,
    val categoryName: String,
    val categoryColor: String
) : Parcelable

@Serializable
@Parcelize
data class RecipeServiceItem(
    val recipes: List<RecipeCreateItem>,
    val teamId: String
) : Parcelable

@Serializable
@Parcelize
data class RecipeCreateItem(
    val imageData: ByteArray?,
    val imageName: String,
    val recipeName: String,
    val ingredients: List<IngredientItem>,
    val recipeSteps: List<String>
) : Parcelable

@Serializable
@Parcelize
data class RecipeDetailItem(
    val recipeId: String,
    val recipeName: String,
    val picture: String,
    val ingredient: List<IngredientItem>,
    val steps: List<String>,
) : Parcelable {
    companion object {
        fun domainToItem(domain: Recipe) = RecipeDetailItem(
            recipeId = domain.recipeId,
            recipeName = domain.recipeName,
            picture = domain.picture,
            ingredient = domain.ingredient.map { IngredientItem.domainToParcelize(it) },
            steps = domain.steps
        )
    }
}

@Serializable
@Parcelize
data class IngredientItem(
    val ingredientId: String,
    val ingredientName: String,
    val once: Int,
    val twice: Int,
    val unit: String,
) : Parcelable {
    companion object {
        fun domainToParcelize(domain: Ingredient) = IngredientItem(
            ingredientId = domain.ingredientId,
            ingredientName = domain.ingredientName,
            once = domain.once,
            twice = domain.twice,
            unit = domain.unit
        )
    }

    fun toDomain() = Ingredient(
        ingredientId = ingredientId,
        ingredientName = ingredientName,
        once = once,
        twice = twice,
        unit = unit
    )
}

@Serializable
@Parcelize
data class MemberItem(
    val userTeamId: String,
    val userId: String,
    val userName: String,
    val userImage: String,
    val staffLevelId: String,
    val staffLevelName: String,
    val manager: Boolean
) : Parcelable {
    companion object {
        fun domainToItem(domain: Member) = MemberItem(
            userTeamId = domain.userTeamId,
            userId = domain.userId,
            userName = domain.userName,
            userImage = domain.userImage,
            staffLevelId = domain.staffLevelId,
            staffLevelName = domain.staffLevelName,
            manager = domain.manager
        )
    }
}

@Serializable
@Parcelize
data class NoticeItem(
    val noticeId: String,
    val writerName: String,
    val date: String,
    val title: String,
    val content: String,
) : Parcelable

@Serializable
@Parcelize
data class ScheduleTimeItem(
    val scheduleTimeId: String,
    val scheduleTimeName: String,
    val startTime: String,
    val endTime: String,
) : Parcelable {
    companion object {
        fun init() = ScheduleTimeItem(
            scheduleTimeId = "",
            scheduleTimeName = "",
            startTime = LocalTime.now().customFormat(),
            endTime = LocalTime.now().customFormat()
        )

        fun domainToItem(scheduleTime: ScheduleTime) = ScheduleTimeItem(
            scheduleTimeId = scheduleTime.scheduleTimeId,
            scheduleTimeName = scheduleTime.scheduleTimeName,
            startTime = scheduleTime.startTime,
            endTime = scheduleTime.endTime
        )
    }
}

inline fun <reified T : Parcelable?> parcelableNavType(isNullableAllowed: Boolean = false): NavType<T> =
    object : NavType<T>(
        isNullableAllowed = isNullableAllowed,
    ) {
        override fun get(bundle: Bundle, key: String): T? =
            bundle.parcelable(key)

        override fun parseValue(value: String): T = Json.decodeFromString(Uri.decode(value))

        override fun serializeAsValue(value: T): String = Uri.encode(Json.encodeToString(value))

        override fun put(bundle: Bundle, key: String, value: T) {
            bundle.putParcelable(key, value)
        }
    }

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION")
    getParcelable(key) as? T
}