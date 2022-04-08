package com.anhpham.photofetcher.framework.flickr

import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApiService {
    @GET("?method=flickr.photos.search&format=json&nojsoncallback=1&api_key=$FLICKR_API_KEY&text=xf816")
    suspend fun fetchImages(
        @Query(value = "page") page: Int,
        @Query(value = "per_page") perPage: Int
    ): FlickrPhotoResponse

    companion object {
        private const val FLICKR_API_KEY = "2ff6ea64470dd0cb0c772cd7e1d62a16"
    }
}