package com.anhpham.photofetcher.framework.picsum

import com.google.gson.annotations.SerializedName

data class PicsumPhotoResponse(
    val id: Int,
    val author: String?,
    val width: Int,
    val height: Int,
    val url: String?,
    @SerializedName("download_url")
    val downloadUrl: String?
)