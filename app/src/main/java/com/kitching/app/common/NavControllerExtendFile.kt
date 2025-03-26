package com.kitching.app.common

import android.os.Parcelable
import androidx.navigation.NavController

const val ARG_KEY = "args"

/**
 * Parcelable 객체를 NavController의 SavedStateHandle에 저장하고 해당 화면으로 이동하는 확장 함수
 *
 * @param route
 * @param args
 */
fun NavController.navigateWithArgs(route: String, args: Parcelable) {
    this.currentBackStackEntry?.savedStateHandle?.set(ARG_KEY, args)
    this.navigate(route)
}

/**
 * NavController의 SavedStateHandle에서 Parcelable 객체를 가져오는 확장 함수
 *
 * @param T
 */
fun <T>NavController.getArgsFromSavedStateHandle(): T? = this.previousBackStackEntry?.savedStateHandle?.get<T>(ARG_KEY)