package com.anhpham.photofetcher.presentation.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.anhpham.photofetcher.R

sealed class AppScreens(val route: String, @StringRes val nameResId: Int, @DrawableRes val iconResId: Int) {
    object FlickrPhotos : AppScreens("flickr", R.string.screen_flickr, R.drawable.icon_flickr)
    object PicsumPhotos : AppScreens("picsum", R.string.screen_picsum, R.drawable.icon_picsum)
    object PhotoDetail : AppScreens("photo", R.string.screen_photo_detail, 0)
}
