package com.kitching.app.common

import com.kitching.app.R

class ProgressIndicatorItem(val image: Int, val stringResource: Int) {
    companion object {
        private val salt = ProgressIndicatorItem(image = R.drawable.icon_salt, stringResource = R.string.indicator_screen_message_salt)
        private val bread = ProgressIndicatorItem(image = R.drawable.icon_whipper, stringResource = R.string.indicator_screen_message_whipper)
        private val whipper = ProgressIndicatorItem(image = R.drawable.icon_bread, stringResource = R.string.indicator_screen_message_bread)

        fun getRandomItem() = listOf(salt, bread, whipper).random()
    }
}