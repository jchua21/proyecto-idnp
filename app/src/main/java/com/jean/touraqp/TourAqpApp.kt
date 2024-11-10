package com.jean.touraqp

import android.app.Application
import android.content.Context
import coil3.ImageLoader
import coil3.SingletonImageLoader
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class TourAqpApp: Application() ,SingletonImageLoader.Factory {

    @Inject lateinit var imageLoader: ImageLoader

    override fun newImageLoader(context: Context): ImageLoader {
        return imageLoader
    }

}