package com.kitching.app.common

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.kitching.app.navgraph.Route

/**
 * 이동할 경로가 현재 경로와 같으면 이동하지 않고,
 * 스택의 마지막이 경로와 같으면 재사용하는 navigate 함수
 *
 * 만약 이동할 경로가 그래프라면,
 * 해당 그래프의 startDestination과 비교하여 중복 이동을 방지
 *
 * @param route 이동할 경로
 * @param builder 추가적인 Navigation 옵션 설정
 */
fun <T : Any> NavController.navIfNew(
    route: T,
    builder: (NavOptionsBuilder.() -> Unit)? = null
) {
    val currentRoute = this.currentDestination?.route?.substringAfterLast(".")
    val routeString = if(route is Route.BottomTab) {
        route.getStartDestination().toString()
    } else route.toString()
    if(currentRoute != routeString) {
        this.navigate(route = route) {
            launchSingleTop = true
            builder?.invoke(this)
        }
    }
}