package com.kitching.app.navgraph

import kotlinx.serialization.Serializable

sealed interface LoginRouteDef {
    @Serializable
    data object Splash : LoginRouteDef

    @Serializable
    data object Login : LoginRouteDef

    @Serializable
    data object SelectTeam : LoginRouteDef

    @Serializable
    data object CreateTeam : LoginRouteDef
}