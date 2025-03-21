package com.kitching.app.navgraph

import kotlinx.serialization.Serializable

@Serializable
open class BottomNavItems

@Serializable
object ScheduleTab: BottomNavItems()

@Serializable
object PrepTab: BottomNavItems()

@Serializable
object RecipeTab: BottomNavItems()

@Serializable
object OrderTab: BottomNavItems()

@Serializable
object OtherTab: BottomNavItems()