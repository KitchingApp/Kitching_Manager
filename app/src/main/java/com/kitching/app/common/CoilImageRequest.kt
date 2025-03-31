package com.kitching.app.common

import android.content.Context
import coil3.ImageLoader
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.request.crossfade
import coil3.size.Size
import java.io.File

class CoilImageRequest {
    companion object {
        fun getImageRequest(sourceImage: String) =
            ImageRequest.Builder(KitchingApplication.getCoilUsingApplication())
                .data(data = sourceImage)
                .size(Size.ORIGINAL)
//                .error(R.drawable.error_image)
                .crossfade(true)
                .build()
    }
}