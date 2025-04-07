package com.kitching.app.service

import android.os.Parcelable
import com.kitching.app.navgraph.Route
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

const val NAVIGATE_TO_SCREEN_KEY = "navigate_to_screen"

@Serializable
enum class NavigateToScreenIntent(val route: Route.BottomTab) {
    SCHEDULE_GRAPH(Route.ScheduleGraph),
    PREP_GRAPH(Route.PrepGraph),
    RECIPE_GRAPH(Route.RecipeGraph),
    ORDER_GRAPH(Route.OrderGraph),
    OTHER_GRAPH(Route.OtherGraph);

    companion object {
        fun findRouteByName(name: String): Route.BottomTab =
            entries.find { it.name == name }?.route ?: Route.ScheduleGraph
    }
}