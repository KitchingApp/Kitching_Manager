package com.kitching.app.ui.screen.common

import androidx.compose.runtime.Composable

/**
 * Result condition screen
 *
 * @param loadingCondition 로딩 화면을 보여줄 상태 정의
 * @param successCondition 로드가 끝났을 때의 상태 정의(화면 띄워주기)
 * @param failCondition 실패 화면을 보여줄 상태 정의
 * @param failContent 실패 화면
 * @param successContent 로드가 끝났을 때 보여줄 화면
 */
@Composable
fun ResultConditionScreen(
    loadingCondition: Boolean,
    successCondition: Boolean,
    failCondition: Boolean,
    failContent: @Composable () -> Unit,
    successContent: @Composable () -> Unit
) {
    if(loadingCondition) {
        ProgressIndicatorScreen()
    } else if(successCondition) {
        successContent()
    } else if(failCondition) {
        failContent()
        // 실패 화면 띄워주기(인수로 다시 시도 버튼 동작 받기)
    }
}