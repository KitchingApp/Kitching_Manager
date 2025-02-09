package com.kitching.app.navgraph

sealed class LoginRouteDef(val routeName: String) {
    object Splash : LoginRouteDef("splash_screen")
    object Login : LoginRouteDef("login_screen")
    object SelectTeam : LoginRouteDef("select_team_screen")
    object CreateTeam : LoginRouteDef("create_team_screen")
}