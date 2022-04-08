package com.anhpham.photofetcher.presentation.ui.photo

import android.os.Bundle
import androidx.navigation.NavType
import com.anhpham.domain.model.Photo
import com.google.gson.Gson

class PhotoParamType : NavType<Photo>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Photo? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): Photo {
        return Gson().fromJson(value, Photo::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: Photo) {
        bundle.putParcelable(key, value)
    }
}