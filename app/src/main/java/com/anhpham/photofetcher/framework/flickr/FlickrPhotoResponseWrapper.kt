package com.anhpham.photofetcher.framework.flickr

import com.google.gson.annotations.SerializedName

data class FlickrPhotoResponse(
    val photos: FlickrPhotoMetaData
)

data class FlickrPhotoMetaData(
    val page: Int,
    val pages: Int,

    @SerializedName("perpage")
    val perPage: Int,

    val total: Int,
    val photo: List<FlickrPhoto>?
)

data class FlickrPhoto(
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val farm: Int,
    val title: String
)