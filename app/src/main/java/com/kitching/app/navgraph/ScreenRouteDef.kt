package com.kitching.app.navgraph

sealed class ScreenRouteDef(val routeName: String) {
    data object PrepTab: ScreenRouteDef("Prep")
    data object RecipeTab: ScreenRouteDef("Recipe")
    data object ScheduleTab: ScreenRouteDef("Schedule")
    data object OrderTab: ScreenRouteDef("Order")
    data object OtherTab: ScreenRouteDef("Other")

    sealed interface InnerContent{
        data object RecipeDetail : ScreenRouteDef("detail")
    }
}