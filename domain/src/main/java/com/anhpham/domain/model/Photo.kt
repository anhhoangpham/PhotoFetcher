package com.anhpham.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(
    val id: String,
    val title: String?,
    val url: String?,
    val thumbnailUrl: String?
) : Parcelable