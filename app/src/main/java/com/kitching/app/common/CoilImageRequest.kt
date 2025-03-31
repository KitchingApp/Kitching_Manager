package com.kitching.app.common

import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Size

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