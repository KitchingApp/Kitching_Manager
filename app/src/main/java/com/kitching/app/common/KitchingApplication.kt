package com.kitching.app.common

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache

//Default Memory Size = 0.15 ~ 0.2
const val COIL_MEMORY_CACHE_SIZE_PERCENT = 0.1

//Coil Dist Cache Size Setting
const val COIL_DISK_CACHE_DIR_NAME = "coil_file_cache"
const val COIL_DISK_CACHE_MAX_SIZE = 1024 * 1024 * 30

class KitchingApplication : Application(), SingletonImageLoader.Factory {
    override fun onCreate() {
        super.onCreate()
        kitchingApplication = this
    }

    companion object {
        private lateinit var kitchingApplication: KitchingApplication
        fun getInstance(): KitchingApplication {
            return kitchingApplication
        }
        fun getCoilUsingApplication() = kitchingApplication
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader =
        ImageLoader.Builder(context).memoryCache {
            MemoryCache.Builder()
                .maxSizePercent(context, COIL_MEMORY_CACHE_SIZE_PERCENT)
                .build()
        }
            .diskCache {
                DiskCache.Builder()
                    .directory(filesDir.resolve(COIL_DISK_CACHE_DIR_NAME))
                    .maximumMaxSizeBytes(COIL_DISK_CACHE_MAX_SIZE.toLong())
                    .build()
            }.build()
}