package com.kitching.app.common

import android.widget.Toast

fun showToast(message: String, delayTime: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(KitchingApplication.getInstance(), message, delayTime).show()
}